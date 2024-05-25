package com.palleteforco.palleteforco.domain.cart.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper,
                           ProductMapper productMapper,
                           OAuth2Service oAuth2Service) {
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
        this.oAuth2Service = oAuth2Service;
    }

    @Transactional
    public void registerCart(CartDto cartDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        log.info("***** 이메일 ***** : " + email);
        ProductDto productDto = productMapper.selectProductListDetail(cartDto.getProduct_id());
        log.info("***** 제품 아이디 ***** : " + productDto);

        cartDto.setEmail(email);
        cartDto.setCart_date(LocalDateTime.now());
        cartDto.setCart_status(0);

        if (productDto.getQty() == 0) {
            throw new NotFoundException("제품 재고 수량이 0입니다.");
        }

        CartDto existing = cartMapper.selectExistForCart(email, cartDto.getProduct_id());

        if (existing != null) {
            if (existing.getCart_status() == 0) {
                existing.setCart_qty(existing.getCart_qty() + cartDto.getCart_qty());
                cartMapper.updateCart(existing);
                cartDto.setCart_id(existing.getCart_id());
                cartDto.setCart_qty(existing.getCart_qty());
            } else if (existing.getCart_status() == 1) {
                cartMapper.insertCart(cartDto);
            }
        } else {
            cartMapper.insertCart(cartDto);
        }
    }

    @Transactional
    public void modifyCart(CartDto cartDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        CartDto existingCartId = cartMapper.selectCartById(cartDto.getCart_id());
        log.info("existingCartId : " + existingCartId);
        CartDto existingProductId = cartMapper.selectExistForCart(email, cartDto.getProduct_id());
        log.info("existingProductId : " + existingProductId);

        if (existingCartId == null || existingProductId == null) {
            throw new NotFoundException("장바구니에 담긴 제품이 없습니다.");
        }

        if (!existingCartId.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        cartDto.setEmail(email);
        cartDto.setCart_date(LocalDateTime.now());

        cartMapper.updateCart(cartDto);
    }

    @Transactional
    public void removeCart(Long cart_id) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        CartDto existing = cartMapper.selectCartById(cart_id);

        if (existing == null) {
            throw new NotFoundException("장바구니에 담긴 제품이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        cartMapper.deleteCart(cart_id);
    }

    @Transactional
    public List<CartDto> getMyCart() throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        return cartMapper.selectMyCart(email);
    }
}
