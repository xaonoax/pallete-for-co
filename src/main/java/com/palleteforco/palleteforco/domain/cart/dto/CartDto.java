package com.palleteforco.palleteforco.domain.cart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    private String email;
    private Long product_id;
    private Long cart_id;
    private Long cart_qty;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cart_date;
    private int cart_status;
    private LocalDateTime time_out;

    @Override
    public String toString() {
        return "CartDto{" +
                "email='" + email + '\'' +
                ", product_id=" + product_id +
                ", cart_id=" + cart_id +
                ", cart_qty=" + cart_qty +
                ", cart_date=" + cart_date +
                ", cart_status=" + cart_status +
                '}';
    }
}
