package com.palleteforco.palleteforco.domain.review.service;

import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.mapper.ReviewMapper;
import com.palleteforco.palleteforco.global.exception.ForbiddenException;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper reviewMapper;
    private final ProductMapper productMapper;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper, ProductMapper productMapper) {
        this.reviewMapper = reviewMapper;
        this.productMapper = productMapper;
    }

    @Transactional
    public void registerReview(ReviewDto reviewDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        reviewDto.setEmail(email);

        reviewMapper.insertReview(reviewDto);
    }

    public List<ReviewDto> getReviewList(Long product_id) throws Exception {
        ProductDto productDto = productMapper.selectProductListDetail(product_id);

        return reviewMapper.selectReviewList(product_id);
    }

    @Transactional
    public void modifyReview(ReviewDto reviewDto, Long product_id) throws Exception {
        ProductDto productDto = productMapper.selectProductListDetail(product_id);
        ReviewDto existing = reviewMapper.selectReview(reviewDto.getReview_id());

        if (existing == null) {
            throw new NotFoundException("수정할 리뷰가 없습니다.");
        }

        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenException("접근 권한이 없습니다.");
        }

        reviewMapper.updateReview(reviewDto);
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
            throw new ForbiddenException("접근 권한이 없습니다.");
        }

        reviewMapper.deleteReview(review_id);
    }
}
