package com.palleteforco.palleteforco.domain.dib.controller;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.service.DibService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Slf4j
public class DibController {
    private final DibService dibService;

    @Autowired
    public DibController(DibService dibService) {
        this.dibService = dibService;
    }

    @PostMapping("/{products_id}/dib")
    public DibDto addDib(@PathVariable("products_id") Long product_id,
                         @RequestBody DibDto dibDto) throws Exception {
        dibDto.setProduct_id(product_id);
        dibService.addDib(dibDto);

        return dibDto;
    }

    @DeleteMapping("/{products_id}/dib/{dib_id}")
    public void cancelDib(@PathVariable("products_id") Long product_id,
                          @PathVariable("dib_id") Long dib_id) throws Exception {
        dibService.cancelDib(product_id, dib_id);
    }
}
