package com.palleteforco.palleteforco.domain.review.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import com.palleteforco.palleteforco.domain.file.FileService;
import com.palleteforco.palleteforco.domain.member.mapper.MemberMapper;
import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import com.palleteforco.palleteforco.domain.orders.mapper.OrdersMapper;
import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.dto.ReviewResponse;
import com.palleteforco.palleteforco.domain.review.mapper.ReviewMapper;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper reviewMapper;
    private final ProductMapper productMapper;
    private final MemberMapper memberMapper;
    private final OrdersMapper ordersMapper;
    private final CartMapper cartMapper;
    private final OAuth2Service oAuth2Service;
    private final FileService fileService;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper,
                             ProductMapper productMapper,
                             MemberMapper memberMapper,
                             OrdersMapper ordersMapper,
                             CartMapper cartMapper,
                             OAuth2Service oAuth2Service,
                             FileService fileService) {
        this.reviewMapper = reviewMapper;
        this.productMapper = productMapper;
        this.memberMapper = memberMapper;
        this.ordersMapper = ordersMapper;
        this.cartMapper = cartMapper;
        this.oAuth2Service = oAuth2Service;
        this.fileService = fileService;
    }

    @Value("${file.upload.dir}")
    private String fileUploadDir;

    @Transactional(rollbackFor = Exception.class)
    public ReviewResponse registerReview(ReviewDto reviewDto, Long product_id) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        log.info("로그인 : " + email);
        String writer = memberMapper.selectByName(email);

        reviewDto.setEmail(email);
        reviewDto.setReview_reg_date(LocalDateTime.now());
        reviewDto.setReview_writer(writer);

        OrdersDto ordersDto = ordersMapper.selectOrdersById(reviewDto.getOrders_id());
        log.info("ordersDto : " + ordersDto);
        if (ordersDto == null) {
            throw new NotFoundException("구매한 제품이 아닙니다");
        }

        CartDto cartDto = cartMapper.selectCartById(ordersDto.getCart_id());
        log.info("cartDto : " + cartDto);
        if (cartDto == null || !cartDto.getProduct_id().equals(product_id)) {
            throw new NotFoundException("구매한 제품이 아닙니다");
        }

        if (!cartDto.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        log.info("구매 상태 : " + ordersDto.getOrders_status());
        if (ordersDto.getOrders_status() != 3) {
            throw new NotFoundException("구매가 확정되지 않았습니다.");
        }

        ProductDto productDto = productMapper.selectProductListDetail(cartDto.getProduct_id());
        if (!cartDto.getProduct_id().equals(productDto.getProduct_id())) {
            throw new NotFoundException("구매한 제품이 아닙니다.");
        }

        ReviewDto existingReview = reviewMapper.selectExistForReview(reviewDto.getOrders_id());
        if (existingReview != null) {
            throw new NotFoundException("작성한 리뷰가 있습니다.");
        }

        ReviewResponse response = new ReviewResponse();

        if (reviewDto.getReviewFile() == null || reviewDto.getReviewFile().isEmpty()) {
            reviewMapper.insertReview(reviewDto);
        } else {
            fileService.FileAsyncForReview(reviewDto, response);
            reviewMapper.insertReview(reviewDto);
        }

        BeanUtils.copyProperties(reviewDto, response);

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ReviewDto> getReviewList(Long product_id) throws Exception {
        productMapper.selectProductListDetail(product_id);

        return reviewMapper.selectReviewList(product_id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReviewResponse modifyReview(ReviewDto reviewDto, Long product_id) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        String writer = memberMapper.selectByName(email);
        ReviewDto existing = reviewMapper.selectReviewById(reviewDto.getReview_id());

        reviewDto.setEmail(email);
        reviewDto.setReview_update_date(LocalDateTime.now());
        reviewDto.setReview_writer(writer);

        log.info("existing : " + existing);
        if (existing == null) {
            throw new NotFoundException("수정할 리뷰가 없습니다.");
        }

        OrdersDto ordersDto = ordersMapper.selectOrdersById(reviewDto.getOrders_id());
        log.info("ordersDto : " + ordersDto);
        if (ordersDto == null || !existing.getOrders_id().equals(ordersDto.getOrders_id())) {
            throw new NotFoundException("수정할 리뷰가 없습니다.");
        }

        CartDto cartDto = cartMapper.selectCartById(ordersDto.getCart_id());
        log.info("cartDto : " + cartDto);
        if (cartDto == null || !cartDto.getProduct_id().equals(product_id)) {
            throw new NotFoundException("수정할 리뷰가 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        ReviewResponse response = new ReviewResponse();

        if (reviewDto.getReviewFile() == null || reviewDto.getReviewFile().isEmpty()) {
            reviewMapper.updateReview(reviewDto);
        } else {
            fileService.FileAsyncForReview(reviewDto, response);
            reviewMapper.updateReview(reviewDto);
        }

        BeanUtils.copyProperties(reviewDto, response);

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeReview(Long review_id, Long product_id) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        ReviewDto existing = reviewMapper.selectReviewById(review_id);
        if (existing == null) {
            throw new NotFoundException("삭제할 리뷰가 없습니다.");
        }

        OrdersDto ordersDto = ordersMapper.selectOrdersById(existing.getOrders_id());
        if (ordersDto == null) {
            throw new NotFoundException("구매한 제품이 아닙니다");
        }

        CartDto cartDto = cartMapper.selectCartById(ordersDto.getCart_id());
        ProductDto productDto = productMapper.selectProductListDetail(cartDto.getProduct_id());

        log.info(" : " + cartDto.getProduct_id());
        log.info("제품 아이디 : " + productDto.getProduct_id());
        if (!cartDto.getProduct_id().equals(productDto.getProduct_id()) || !product_id.equals(productDto.getProduct_id())) {
            throw new NotFoundException("삭제할 리뷰가 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        reviewMapper.deleteReview(review_id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ReviewDto> getMyReview() throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        return reviewMapper.selectMyReview(email);
    }
}
