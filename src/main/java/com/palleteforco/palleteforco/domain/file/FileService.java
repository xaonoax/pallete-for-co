package com.palleteforco.palleteforco.domain.file;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryResponse;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.dto.ReviewResponse;

public interface FileService {
    public void FileAsyncForInquiry(InquiryDto inquiryDto, InquiryResponse response) throws Exception;
    public void FileAsyncForReview(ReviewDto reviewDto, ReviewResponse response) throws Exception;
}
