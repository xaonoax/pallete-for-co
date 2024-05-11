package com.palleteforco.palleteforco.domain.cart.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import com.palleteforco.palleteforco.global.exception.ForbiddenException;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMappert) {
        this.cartMapper = cartMappert;
    }

    @Transactional
    public void registerCart(CartDto cartDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        cartDto.setEmail(email);

        cartDto.setCart_date(LocalDateTime.now());

        cartMapper.insertCart(cartDto);
    }

    @Transactional
    public void modifyCart(CartDto cartDto) throws Exception {
        CartDto existing = cartMapper.selectCart(cartDto.getCart_id());

        if (existing == null) {
            throw new NotFoundException("장바구니에 담긴 제품이 없습니다.");
        }

        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenException("접근 권한이 없습니다.");
        }

        cartMapper.updateCart(cartDto);
    }

    @Transactional
    public void removeCart(Long cart_id) throws Exception {
        CartDto existing = cartMapper.selectCart(cart_id);

        if (existing == null) {
            throw new NotFoundException("장바구니에 담긴 제품이 없습니다.");
        }

        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenException("접근 권한이 없습니다.");
        }

        cartMapper.deleteCart(cart_id);
    }
}
