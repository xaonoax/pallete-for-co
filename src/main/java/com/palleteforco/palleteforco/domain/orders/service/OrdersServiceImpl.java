package com.palleteforco.palleteforco.domain.orders.service;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.mapper.CartMapper;
import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import com.palleteforco.palleteforco.domain.orders.mapper.OrdersMapper;
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
public class OrdersServiceImpl implements OrdersService {
    private final OrdersMapper ordersMapper;
    private final CartMapper cartMapper;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public OrdersServiceImpl(OrdersMapper ordersMapper,
                             CartMapper cartMapper,
                             OAuth2Service oAuth2Service) {
        this.ordersMapper = ordersMapper;
        this.cartMapper = cartMapper;
        this.oAuth2Service = oAuth2Service;
    }

    @Transactional
    public void placeOrders(OrdersDto ordersDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        CartDto existing = cartMapper.selectCartById(ordersDto.getCart_id());

        ordersDto.setOrders_date(LocalDateTime.now());
        ordersDto.setOrders_status(1);

        if (existing == null) {
            throw new NotFoundException("구매할 제품이 없습니다.");
        }

        if (existing.getCart_status() == 1) {
            throw new NotFoundException("이미 구매한 제품입니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        ordersMapper.insertOrders(ordersDto);
        cartMapper.updateCartStatus(ordersDto.getCart_id());
    }

    public void cancelOrders(OrdersDto ordersDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        OrdersDto existing = ordersMapper.selectOrdersById(ordersDto.getOrders_id());

        ordersDto.setOrders_date(LocalDateTime.now());
        ordersDto.setOrders_status(0);

        if (existing == null) {
            throw new NotFoundException("구매 취소할 제품이 없습니다.");
        }

        CartDto cartDto = cartMapper.selectCartById(existing.getCart_id());

        if (existing.getOrders_status() == 0) {
            if (!cartDto.getEmail().equals(email)) {
                throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
            }
            throw new NotFoundException("이미 취소된 주문입니다.");
        }

        if (!cartDto.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        ordersMapper.updateOrdersByCancel(ordersDto);
    }

    public List<OrdersDto> getMyOrders() throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        return ordersMapper.selectMyOrders(email);
    }
}
