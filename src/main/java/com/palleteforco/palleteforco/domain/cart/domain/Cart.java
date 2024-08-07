package com.palleteforco.palleteforco.domain.cart.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Cart {
    private String email;
    private Long product_id;
    private Long cart_id;
    private Long cart_qty;
    private LocalDateTime cart_date;
    private Long cart_status;
    private LocalDateTime time_out;
}
