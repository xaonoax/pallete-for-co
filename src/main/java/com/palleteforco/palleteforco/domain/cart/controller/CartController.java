package com.palleteforco.palleteforco.domain.cart.controller;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartDto registerCart(@RequestBody CartDto cartDto) throws Exception {
        cartService.registerCart(cartDto);

        return cartDto;
    }

    @GetMapping
    public List<CartDto> getCartList() throws Exception {
        return cartService.getCartList();
    }

    @PutMapping
    public CartDto modifyCart(@RequestBody CartDto cartDto) throws Exception {
        cartService.modifyCart(cartDto);
        return cartDto;
    }

    @DeleteMapping("/{cart_id}")
    public void removeCart(@PathVariable("cart_id") Long cart_id) throws Exception {
        cartService.removeCart(cart_id);
    }
}
