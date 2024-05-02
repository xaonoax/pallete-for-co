package com.palleteforco.palleteforco.domain.review.mapper;

import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    public void insertReview(ReviewDto reviewDto) throws Exception;
    public List<ReviewDto> selectReviewList() throws Exception;
    public void updateReview(ReviewDto reviewDto) throws Exception;
    public void deleteReview(Long review_id) throws Exception;
}

