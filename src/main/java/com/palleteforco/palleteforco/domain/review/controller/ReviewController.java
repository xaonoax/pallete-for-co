package com.palleteforco.palleteforco.domain.review.controller;

import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewDto registerReview(@RequestBody ReviewDto reviewDto) throws Exception {
        reviewService.registerReview(reviewDto);

        return reviewDto;
    }

    @GetMapping
    public List<ReviewDto> getReviewList() throws Exception {
        return reviewService.getReviewList();
    }

    @PutMapping("/{review_id}")
    public ReviewDto modifyReview(@PathVariable("review_id") Long review_id, @RequestBody ReviewDto reviewDto) throws Exception {
        reviewService.modifyReview(reviewDto);

        return reviewDto;
    }

    @DeleteMapping("/{review_id}")
    public void removeReview(@PathVariable("review_id") Long review_id) throws Exception {
        reviewService.removeReview(review_id);
    }
}
