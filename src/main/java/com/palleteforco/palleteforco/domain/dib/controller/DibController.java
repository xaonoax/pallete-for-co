package com.palleteforco.palleteforco.domain.dib.controller;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.service.DibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dib")
public class DibController {
    private final DibService dibService;

    @Autowired
    public DibController(DibService dibService) {
        this.dibService = dibService;
    }

    @PostMapping
    public DibDto addDib(@RequestBody DibDto dibDto) throws Exception {
        dibService.addDib(dibDto);

        return dibDto;
    }

    @GetMapping
    public List<DibDto> getDibList() throws Exception {
        return dibService.getDibList();
    }

    @DeleteMapping("/{dib_id}")
    public void cancelDib(@PathVariable("dib_id") Long dib_id) throws Exception {
        dibService.cancelDib(dib_id);
    }
}
