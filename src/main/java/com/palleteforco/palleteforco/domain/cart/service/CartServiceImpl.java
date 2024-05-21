package com.palleteforco.palleteforco.domain.cart.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper, ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
    }

    @Transactional
    public void registerCart(CartDto cartDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        cartDto.setEmail(email);

        cartDto.setCart_date(LocalDateTime.now());

        ProductDto productDto = productMapper.selectProductListDetail(cartDto.getProduct_id());

        if (productDto.getQty() == 0) {
            throw new NotFoundException("제품 수량이 0입니다.");
        }

        cartMapper.insertCart(cartDto);
    }

    @Transactional
    public void modifyCart(CartDto cartDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        CartDto existing = cartMapper.selectCartById(cartDto.getCart_id());

        if (existing == null) {
            throw new NotFoundException("장바구니에 담긴 제품이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        cartMapper.updateCart(cartDto);
    }

    @Transactional
    public void removeCart(Long cart_id) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        CartDto existing = cartMapper.selectCartById(cart_id);

        if (existing == null) {
            throw new NotFoundException("장바구니에 담긴 제품이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        cartMapper.deleteCart(cart_id);
    }

    public List<CartDto> getMyCart() throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        return cartMapper.selectMyCart(email);
    }
}
