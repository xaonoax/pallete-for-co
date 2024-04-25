package com.palleteforco.palleteforco.domain.product.controller;

import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProductList() throws Exception {
        log.info("----------get list OK---------");

        return productService.getProductList();
    }

    @GetMapping("/{product_id}")
    public ProductDto getProductListDetail(@PathVariable("product_id") Long product_id) throws Exception{
        return productService.getProductListDetail(product_id);
    }

}
