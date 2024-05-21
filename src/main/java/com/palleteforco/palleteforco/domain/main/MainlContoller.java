package com.palleteforco.palleteforco.domain.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainlContoller {
    @GetMapping
    public String Main() {
        return "Pallete-For-Co";
    }
}
