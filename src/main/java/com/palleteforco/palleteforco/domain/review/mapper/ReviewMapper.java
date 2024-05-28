package com.palleteforco.palleteforco.domain.review.mapper;

import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    public void insertReview(ReviewDto reviewDto) throws Exception;
    public List<ReviewDto> selectReviewList(Long product_id) throws Exception;
    public ReviewDto selectReviewById(Long review_id) throws Exception;
    public void updateReview(ReviewDto reviewDto) throws Exception;
    public void deleteReview(Long review_id) throws Exception;
    public ReviewDto selectExistForReview(@Param("orders_id") Long orders_id) throws Exception;
    public List<ReviewDto> selectMyReview(String email) throws Exception;
}

