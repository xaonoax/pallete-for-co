package com.palleteforco.palleteforco.domain.cart.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;

import java.util.List;

public interface CartService {
    public void registerCart(CartDto cartDto) throws Exception;
    public List<CartDto> getCartList() throws Exception;
    public void modifyCart(CartDto cartDto) throws Exception;
    public void removeCart(Long cart_id) throws Exception;
}
