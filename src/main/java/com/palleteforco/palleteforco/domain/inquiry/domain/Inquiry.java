package com.palleteforco.palleteforco.domain.inquiry.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Inquiry {
    private Long inquiry_id;
    private String inquiry_title;
    private String inquiry_content;
    private LocalDateTime inquiry_regdate;
    private LocalDateTime inquiry_update_date;
    private String inquiry_file_attach;
}
