package com.palleteforco.palleteforco.domain.review.controller;

import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.dto.ReviewResponse;
import com.palleteforco.palleteforco.domain.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{product_id}/review")
    public ReviewResponse registerReview(@RequestPart("reviewDto") ReviewDto reviewDto,
                                         @RequestPart(value = "reviewFile", required = false) MultipartFile reviewFile) throws Exception {

        reviewDto.setReviewFile(reviewFile);
        ReviewResponse response = reviewService.registerReview(reviewDto);

        return response;
    }

    @GetMapping("/{product_id}/review")
    public List<ReviewDto> getReviewList(@PathVariable("product_id") Long product_id) throws Exception {

        return reviewService.getReviewList(product_id);
    }

    @PutMapping("/{product_id}/review/{review_id}")
    public ReviewResponse modifyReview(@PathVariable("product_id") Long product_id,
                                  @PathVariable("review_id") Long review_id,
                                  @RequestPart("reviewDto") ReviewDto reviewDto,
                                  @RequestPart(value = "reviewFile", required = false) MultipartFile reviewFile) throws Exception {

        reviewDto.setReview_id(review_id);
        reviewDto.setReviewFile(reviewFile);
        ReviewResponse response = reviewService.modifyReview(reviewDto, product_id);

        return response;
    }

    @DeleteMapping("/{product_id}/review/{review_id}")
    public void removeReview(@PathVariable("review_id") Long review_id) throws Exception {
        reviewService.removeReview(review_id);
    }
}
