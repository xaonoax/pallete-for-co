package com.palleteforco.palleteforco.domain.inquiry.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Inquiry {
    private String email;
    private Long inquiry_id;
    private String inquiry_title;
    private String inquiry_content;
    private String inquiry_writer;
    private LocalDateTime inquiry_reg_date;
    private LocalDateTime inquiry_update_date;
    private String inquiry_file_attach;
}
