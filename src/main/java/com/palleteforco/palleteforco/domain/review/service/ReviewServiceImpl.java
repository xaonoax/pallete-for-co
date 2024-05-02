package com.palleteforco.palleteforco.domain.review.service;

import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public void registerReview(ReviewDto reviewDto) throws Exception {
        reviewMapper.insertReview(reviewDto);
    }

    public List<ReviewDto> getReviewList() throws Exception {
        return reviewMapper.selectReviewList();
    }

    @Transactional
    public void modifyReview(ReviewDto reviewDto) throws Exception {
        reviewMapper.updateReview(reviewDto);
    }

    @Transactional
    public void removeReview(Long review_id) throws Exception {
        reviewMapper.deleteReview(review_id);
    }
}
