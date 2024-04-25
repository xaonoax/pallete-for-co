package com.palleteforco.palleteforco.domain.product.service;

import com.palleteforco.palleteforco.domain.product.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public void registerProduct(ProductDto productDto) throws Exception;
    public List<ProductDto> getProductList() throws Exception;
    public ProductDto getProductListDetail(Long product_id) throws Exception;
    public void modifyProduct(ProductDto productDto) throws Exception;
    public void removeProduct(Long product_id) throws Exception;
}
