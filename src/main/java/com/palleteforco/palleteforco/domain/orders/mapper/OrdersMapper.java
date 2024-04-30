package com.palleteforco.palleteforco.domain.orders.mapper;

import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper {
    public void insertOrders(OrdersDto ordersDto) throws Exception;
    public List<OrdersDto> selectOrdersList() throws Exception;
    public void updateOrders(OrdersDto ordersDto) throws Exception;
}
