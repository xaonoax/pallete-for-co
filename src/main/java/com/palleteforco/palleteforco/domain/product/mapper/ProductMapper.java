package com.palleteforco.palleteforco.domain.product.mapper;

import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    public void insertProduct(ProductDto productDto) throws Exception;
    public List<ProductDto> selectProductList() throws Exception;
    public ProductDto selectProductListDetail(Long product_id) throws Exception;
    public void updateProduct(ProductDto productDto) throws Exception;
    public void deleteProduct(Long product_id) throws Exception;
}
