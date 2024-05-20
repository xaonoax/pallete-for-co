package com.palleteforco.palleteforco.domain.review.service;

import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.dto.ReviewResponse;
import com.palleteforco.palleteforco.domain.review.mapper.ReviewMapper;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper reviewMapper;
    private final ProductMapper productMapper;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper, ProductMapper productMapper) {
        this.reviewMapper = reviewMapper;
        this.productMapper = productMapper;
    }

    @Value("${file.upload.dir}")
    private String fileUploadDir;

    @Transactional
    public ReviewResponse registerReview(ReviewDto reviewDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        reviewDto.setEmail(email);

        Long ordersId = reviewDto.getOrders_id();

        if (reviewMapper.selectExistForReview(ordersId)) {
            throw new NotFoundException("작성한 리뷰가 있습니다.");
        }

        ReviewResponse response = new ReviewResponse();

        if (reviewDto.getReviewFile() == null || reviewDto.getReviewFile().isEmpty()) {
            reviewMapper.insertReview(reviewDto);
        } else {
            MultipartFile reviewFile = reviewDto.getReviewFile();
            String reviewFileName = reviewFile.getOriginalFilename();
            String reviewStoredFileName = UUID.randomUUID() + "_" + reviewFileName;

            reviewDto.setReview_id(reviewDto.getReview_id());
            reviewDto.setReview_file_name(reviewFileName);
            reviewDto.setReview_stored_file_name(reviewStoredFileName);

            String filePath = fileUploadDir + reviewStoredFileName;
            reviewFile.transferTo(new File(filePath));

            response.setReview_file_name(reviewFileName);
            response.setReview_stored_file_name(reviewStoredFileName);

            reviewMapper.insertReview(reviewDto);
        }

        BeanUtils.copyProperties(response, response);

        return response;
    }

    public List<ReviewDto> getReviewList(Long product_id) throws Exception {
        ProductDto productDto = productMapper.selectProductListDetail(product_id);

        return reviewMapper.selectReviewList(product_id);
    }

    @Transactional
    public ReviewResponse modifyReview(ReviewDto reviewDto, Long product_id) throws Exception {
        ProductDto productDto = productMapper.selectProductListDetail(product_id);
        ReviewDto existing = reviewMapper.selectReview(reviewDto.getReview_id());

        if (existing == null) {
            throw new NotFoundException("수정할 리뷰가 없습니다.");
        }

        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        ReviewResponse response = new ReviewResponse();

        if (reviewDto.getReviewFile() == null || reviewDto.getReviewFile().isEmpty()) {
            reviewMapper.updateReview(reviewDto);
        } else {
            MultipartFile reviewFile = reviewDto.getReviewFile();
            String reviewFileName = reviewFile.getOriginalFilename();
            String reviewStoredFileName = UUID.randomUUID() + "_" + reviewFileName;

            reviewDto.setReview_file_name(reviewFileName);
            reviewDto.setReview_stored_file_name(reviewStoredFileName);
            reviewDto.setReview_id(reviewDto.getReview_id());

            String filePath = fileUploadDir + reviewStoredFileName;
            reviewFile.transferTo(new File(filePath));

            response.setReview_file_name(reviewFileName);
            response.setReview_stored_file_name(reviewStoredFileName);

            reviewMapper.insertReview(reviewDto);
            reviewMapper.updateReview(reviewDto);
        }

        BeanUtils.copyProperties(reviewDto, response);

        return response;
    }

    @Transactional
    public void removeReview(Long review_id) throws Exception {
        ReviewDto existing = reviewMapper.selectReview(review_id);

        if (existing == null) {
            throw new NotFoundException("삭제할 리뷰가 없습니다.");
        }

        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        reviewMapper.deleteReview(review_id);
    }

    public List<ReviewDto> getMyReview() throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        return reviewMapper.selectMyReview(email);
    }
}
