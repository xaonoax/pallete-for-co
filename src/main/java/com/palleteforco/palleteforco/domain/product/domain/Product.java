package com.palleteforco.palleteforco.domain.product.domain;

import lombok.Data;

@Data
public class Product {
    private Long product_id;
    private String product_name;
    private Long price;
    private Long qty;
    private String product_image;
    private String product_detail;
}
