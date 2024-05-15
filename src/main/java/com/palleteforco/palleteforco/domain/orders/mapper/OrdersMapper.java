package com.palleteforco.palleteforco.domain.orders.mapper;

import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper {
    public void insertOrders(OrdersDto ordersDto) throws Exception;
    public OrdersDto selectOrdersById(Long orders_id) throws Exception;
    public void updateOrdersByCancel(Long orders_id) throws Exception;
}
