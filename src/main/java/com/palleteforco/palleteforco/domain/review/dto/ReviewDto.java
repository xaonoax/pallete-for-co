package com.palleteforco.palleteforco.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long orders_id;
    private Long review_id;
    private String review_content;
    private LocalDateTime review_regdate;
    private LocalDateTime review_update_date;
    private int score;
    private String review_file_attach;
}
