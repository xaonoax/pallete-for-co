package com.palleteforco.palleteforco.domain.orders.service;

import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;

public interface OrdersService {
    public void placeOrders(OrdersDto ordersDto) throws Exception;
    public void cancelOrders(Long orders_id) throws Exception;
}
