package com.palleteforco.palleteforco.domain.cart.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMappert) {
        this.cartMapper = cartMappert;
    }

    @Transactional
    public void registerCart(CartDto cartDto) throws Exception {
        cartMapper.insertCart(cartDto);
    }

    public List<CartDto> getCartList() throws Exception {
        return cartMapper.selectCartList();
    }

    @Transactional
    public void modifyCart(CartDto cartDto) throws Exception {
        cartMapper.updateCart(cartDto);
    }

    @Transactional
    public void removeCart(Long cart_id) throws Exception {
        cartMapper.deleteCart(cart_id);
    }
}
