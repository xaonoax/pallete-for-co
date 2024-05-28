package com.palleteforco.palleteforco.domain.orders.controller;

import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import com.palleteforco.palleteforco.domain.orders.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public OrdersDto placeOrders(@RequestBody OrdersDto ordersDto) throws Exception {
        ordersService.placeOrders(ordersDto);

        return ordersDto;
    }

    @PutMapping("/{orders_id}")
    public OrdersDto cancelOrders(@PathVariable("orders_id") Long orders_id, @RequestBody OrdersDto ordersDto) throws Exception {
        log.info("-----------주문 취소-----------");
        ordersDto.setOrders_id(orders_id);
        ordersService.cancelOrders(ordersDto);

        return ordersDto;
    }
}
