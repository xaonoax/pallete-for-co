package com.palleteforco.palleteforco.domain.review.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class Review {
    private String email;
    private Long orders_id;
    private Long review_id;
    private String review_content;
    private String review_writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime review_reg_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime review_update_date;
    private int score;
    private String review_file_name;
    private String review_stored_file_name;
    private MultipartFile reviewFile;
}
