package com.palleteforco.palleteforco.domain.orders.service;

import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import com.palleteforco.palleteforco.domain.orders.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersMapper ordersMapper;

    @Autowired
    public OrdersServiceImpl(OrdersMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }

    @Transactional
    public void placeOrders(OrdersDto ordersDto) throws Exception {
        ordersMapper.insertOrders(ordersDto);
    }

    public List<OrdersDto> getOrderList() throws Exception {
        return ordersMapper.selectOrdersList();
    }

    @Transactional
    public void modifyOrders(OrdersDto ordersDto) throws Exception {
        ordersMapper.updateOrders(ordersDto);
    }
}
