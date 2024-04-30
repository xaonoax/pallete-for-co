package com.palleteforco.palleteforco.domain.orders.service;

import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;

import java.util.List;

public interface OrdersService {
    public void placeOrders(OrdersDto ordersDto) throws Exception;

    public List<OrdersDto> getOrderList() throws Exception;
    public void modifyOrders(OrdersDto ordersDto) throws Exception;
}
