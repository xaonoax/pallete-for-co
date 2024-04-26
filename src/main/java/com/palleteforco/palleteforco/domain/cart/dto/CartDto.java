package com.palleteforco.palleteforco.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    private Long product_id;
    private Long cart_id;
    private Long cart_qty;
    private LocalDateTime cart_date;
}
