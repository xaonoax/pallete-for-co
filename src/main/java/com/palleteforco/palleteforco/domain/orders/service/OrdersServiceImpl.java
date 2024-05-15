package com.palleteforco.palleteforco.domain.orders.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import com.palleteforco.palleteforco.domain.google.mapper.GoogleMapper;
import com.palleteforco.palleteforco.domain.member.mapper.MemberMapper;
import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import com.palleteforco.palleteforco.domain.orders.mapper.OrdersMapper;
import com.palleteforco.palleteforco.global.exception.AlreadyCancelledOrderExceptionHandler;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {
    private final OrdersMapper ordersMapper;
    private final MemberMapper memberMapper;
    private final GoogleMapper googleMapper;
    private final CartMapper cartMapper;

    @Autowired
    public OrdersServiceImpl(OrdersMapper ordersMapper, MemberMapper memberMapper, GoogleMapper googleMapper, CartMapper cartMapper) {
        this.ordersMapper = ordersMapper;
        this.memberMapper = memberMapper;
        this.googleMapper = googleMapper;
        this.cartMapper = cartMapper;
    }

    @Transactional
    public void placeOrders(OrdersDto ordersDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        CartDto existing = cartMapper.selectCartById(ordersDto.getCart_id());

        if (existing == null) {
            throw new NotFoundException("구매할 제품이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        ordersMapper.insertOrders(ordersDto);
        cartMapper.updateCartStatus(ordersDto.getCart_id());
    }

    public void cancelOrders(Long orders_id) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        OrdersDto existing = ordersMapper.selectOrdersById(orders_id);

        if (existing == null) {
            throw new NotFoundException("구매 취소할 제품이 없습니다.");
        }

        if (existing.getOrders_status() == 0) {
            throw new AlreadyCancelledOrderExceptionHandler("이미 취소된 주문입니다.");
        }

        ordersMapper.updateOrdersByCancel(orders_id);
    }
}
