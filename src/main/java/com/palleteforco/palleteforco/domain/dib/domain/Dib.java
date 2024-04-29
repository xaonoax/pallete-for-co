package com.palleteforco.palleteforco.domain.dib.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Dib {
    private Long product_id;
    private Long dib_id;
    private Long dib_status;
    private LocalDateTime dib_date;
}
