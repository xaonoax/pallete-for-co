package com.palleteforco.palleteforco.domain.orders.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Orders {
    private Long cart_id;
    private Long orders_id;
    private int orders_status;
    private LocalDateTime orders_date;
}
