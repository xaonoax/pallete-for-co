package com.palleteforco.palleteforco.domain.cart.mapper;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    public void insertCart(CartDto cartDto) throws Exception;
    public List<CartDto> selectCartList() throws Exception;
    public void updateCart(CartDto cartDto) throws Exception;
    public void deleteCart(Long cart_id) throws Exception;
}