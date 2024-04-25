package com.palleteforco.palleteforco.domain.product.service;

import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Transactional
    public void registerProduct(ProductDto productDto) throws Exception {
        productMapper.insertProduct(productDto);
    }

    public List<ProductDto> getProductList() throws Exception {
        return productMapper.selectProductList();
    }

    @Transactional
    public ProductDto getProductListDetail(Long product_id) throws Exception {
        ProductDto productDto = productMapper.selectProductListDetail(product_id);

        if (productDto == null) {
            throw new NotFoundException("찾으시는 제품이 없습니다.");
        }

        return productMapper.selectProductListDetail(product_id);
    }

    @Transactional
    public void modifyProduct(ProductDto productDto) throws Exception {
        productMapper.updateProduct(productDto);
    }

    @Transactional
    public void removeProduct(Long product_id) throws Exception {
        productMapper.deleteProduct(product_id);
    }
}

