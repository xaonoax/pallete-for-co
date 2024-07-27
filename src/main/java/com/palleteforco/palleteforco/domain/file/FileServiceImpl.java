package com.palleteforco.palleteforco.domain.file;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryResponse;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.dto.ReviewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.dir}")
    private String fileUploadDir;

    @Async
    public void FileAsyncForInquiry(InquiryDto inquiryDto, InquiryResponse response) throws Exception {
        log.info("***** 파일 비동기처리 시작(문의) *****");

        String threadName = Thread.currentThread().getName();
        log.info("스레드 이름(문의) : " + threadName);

        MultipartFile inquiryFile = inquiryDto.getInquiryFile();
        String inquiryFileName = inquiryFile.getOriginalFilename();
        String inquiryStoredFileName = UUID.randomUUID() + "_" + inquiryFileName;

        // 파일 정보 등록
        inquiryDto.setInquiry_id(inquiryDto.getInquiry_id());
        inquiryDto.setInquiry_file_name(inquiryFileName);
        inquiryDto.setInquiry_stored_file_name(inquiryStoredFileName);

        String filePath = fileUploadDir + inquiryStoredFileName;
        inquiryFile.transferTo(new File(filePath));

        // 응답 객체에 파일 정보 설정하기
        response.setInquiry_file_name(inquiryFileName);
        response.setInquiry_stored_file_name(inquiryStoredFileName);

        log.info("***** 파일 비동기처리 완료(문의) *****");
    }

    @Async
    public void FileAsyncForReview(ReviewDto reviewDto, ReviewResponse response) throws Exception {
        log.info("***** 파일 비동기처리 시작(리뷰) *****");

        String threadName = Thread.currentThread().getName();
        log.info("스레드 이름(리뷰) : " + threadName);

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

        log.info("***** 파일 비동기처리 완료(리뷰) *****");
    }
}
