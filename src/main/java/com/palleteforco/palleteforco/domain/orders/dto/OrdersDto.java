package com.palleteforco.palleteforco.domain.orders.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrdersDto {
    private Long cart_id;
    private Long orders_id;
    private int orders_status;
    private LocalDateTime orders_date;
}


