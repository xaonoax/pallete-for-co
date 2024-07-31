package com.palleteforco.palleteforco.domain.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryResponse;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.dto.ReviewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3Client;

    @Autowired
    public FileServiceImpl(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Async
    public void FileAsyncForInquiry(InquiryDto inquiryDto, InquiryResponse response) throws Exception {
        log.info("***** 파일 비동기처리 시작(문의) *****");

        String threadName = Thread.currentThread().getName();
        log.info("스레드 이름(문의) : " + threadName);

        MultipartFile inquiryFile = inquiryDto.getInquiryFile();
        String inquiryFileName = inquiryFile.getOriginalFilename();
        String inquiryStoredFileName = "inquiry_" + UUID.randomUUID() + "_" + inquiryFileName;
        String filePath = "https://" + bucket + "/inquiry" + inquiryStoredFileName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(inquiryFile.getContentType());
            metadata.setContentLength(inquiryFile.getSize());

            amazonS3Client.putObject(bucket, inquiryStoredFileName, inquiryFile.getInputStream(), metadata);

            // 파일 정보 등록
            inquiryDto.setInquiry_id(inquiryDto.getInquiry_id());
            inquiryDto.setInquiry_file_name(inquiryFileName);
            inquiryDto.setInquiry_stored_file_name(inquiryStoredFileName);

            // 응답 객체에 파일 정보 설정하기
            response.setInquiry_file_name(inquiryFileName);
            response.setInquiry_stored_file_name(filePath);

            log.info("***** 파일 비동기처리 완료(문의) *****");
        } catch (IOException e) {
            e.printStackTrace();

            log.error("파일 업로드 오류 발생 : " + e.getMessage());

            throw new Exception("파일 업로드 실패");
        }
    }

    @Async
    public void FileAsyncForReview(ReviewDto reviewDto, ReviewResponse response) throws Exception {
        log.info("***** 파일 비동기처리 시작(리뷰) *****");

        String threadName = Thread.currentThread().getName();
        log.info("스레드 이름(리뷰) : " + threadName);

        MultipartFile reviewFile = reviewDto.getReviewFile();
        String reviewFileName = reviewFile.getOriginalFilename();
        String reviewStoredFileName = "review_" + UUID.randomUUID() + "_" + reviewFileName;
        String filePath = "https://" + bucket + "/review" + reviewStoredFileName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(reviewFile.getContentType());
            metadata.setContentLength(reviewFile.getSize());

            amazonS3Client.putObject(bucket, reviewStoredFileName, reviewFile.getInputStream(), metadata);

            reviewDto.setReview_id(reviewDto.getReview_id());
            reviewDto.setReview_file_name(reviewFileName);
            reviewDto.setReview_stored_file_name(reviewStoredFileName);

            response.setReview_file_name(reviewFileName);
            response.setReview_stored_file_name(filePath);

            log.info("***** 파일 비동기처리 완료(문의) *****");
        } catch (IOException e) {
            e.printStackTrace();

            log.error("파일 업로드 오류 발생 : " + e.getMessage());

            throw new Exception("파일 업로드 실패");
        }
    }
}
