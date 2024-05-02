package com.palleteforco.palleteforco.domain.review.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Long orders_id;
    private Long review_id;
    private String review_content;
    private LocalDateTime review_regdate;
    private LocalDateTime review_update_date;
    private int score;
    private String review_file_attach;
}
