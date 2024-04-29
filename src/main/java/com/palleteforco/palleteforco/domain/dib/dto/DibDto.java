package com.palleteforco.palleteforco.domain.dib.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DibDto {
    private Long product_id;
    private Long dib_id;
    private Long dib_status;
    private LocalDateTime dib_date;
}
