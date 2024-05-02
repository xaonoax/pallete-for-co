package com.palleteforco.palleteforco.domain.review.service;

import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    public void registerReview(ReviewDto reviewDto) throws Exception;
    public List<ReviewDto> getReviewList() throws Exception;
    public void modifyReview(ReviewDto reviewDto) throws Exception;
    public void removeReview(Long review_id) throws Exception;
}
