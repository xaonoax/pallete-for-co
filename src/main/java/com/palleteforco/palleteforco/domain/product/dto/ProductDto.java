package com.palleteforco.palleteforco.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long product_id;
    private String product_name;
    private Long price;
    private Long qty;
    private String product_image;
    private String product_detail;
}
