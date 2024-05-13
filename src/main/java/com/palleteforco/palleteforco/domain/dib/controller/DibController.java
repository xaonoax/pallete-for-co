package com.palleteforco.palleteforco.domain.dib.controller;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.service.DibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DibController {
    private final DibService dibService;

    @Autowired
    public DibController(DibService dibService) {
        this.dibService = dibService;
    }

    @PostMapping("/{products_id}/dib")
    public DibDto addDib(@RequestBody DibDto dibDto) throws Exception {
        dibService.addDib(dibDto);

        return dibDto;
    }

    @DeleteMapping("/{products_id}/dib/{dib_id}")
    public void cancelDib(@PathVariable("dib_id") Long dib_id) throws Exception {
        DibDto dibDto = new DibDto();

        dibService.cancelDib(dib_id);
    }
}
