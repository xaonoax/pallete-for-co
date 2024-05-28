package com.palleteforco.palleteforco.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
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

    @Override
    public String toString() {
        return "ReviewDto{" +
                "email='" + email + '\'' +
                ", orders_id=" + orders_id +
                ", review_id=" + review_id +
                ", review_content='" + review_content + '\'' +
                ", review_writer='" + review_writer + '\'' +
                ", review_reg_date=" + review_reg_date +
                ", review_update_date=" + review_update_date +
                ", score=" + score +
                ", review_file_name='" + review_file_name + '\'' +
                ", review_stored_file_name='" + review_stored_file_name + '\'' +
                ", reviewFile=" + reviewFile +
                '}';
    }
}
