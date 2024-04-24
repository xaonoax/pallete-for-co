package com.palleteforco.palleteforco.domain.inquiry.controller;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.service.InquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inquiries")
@Slf4j
public class InquiryController {
    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) throws Exception{
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public InquiryDto registerInquiry(@RequestBody InquiryDto inquiryDto) throws Exception{
        log.info("----------register OK---------");
        inquiryService.registerInquiry(inquiryDto);

        return inquiryDto;
    }

    @GetMapping
    public List<InquiryDto> getInquiryList() throws Exception {
        log.info("----------get list OK---------");

        return inquiryService.getInquiryList();
    }

    @GetMapping("/{inquiry_id}")
    public InquiryDto getInquiryListDetail(@PathVariable("inquiry_id") Long inquiry_id) throws Exception {
        log.info("----------get list detail OK---------");

        return inquiryService.getInquiryListDetail(inquiry_id);
    }

    @PutMapping("/{inquiry_id}")
    public InquiryDto modifyInquiry(@PathVariable("inquiry_id") Long inquiry_id, @RequestBody InquiryDto inquiryDto) throws Exception {
        log.info("----------modify inquiry OK---------");

        inquiryDto.setInquiry_id(inquiry_id);
        inquiryService.modifyInquiry(inquiryDto);

        return inquiryDto;
    }

    @DeleteMapping("/{inquiry_id}")
    public void removeInquiry(@PathVariable("inquiry_id") Long inquiry_id) throws Exception {
        log.info("----------remove inquiry OK---------");

        InquiryDto inquiryDto = new InquiryDto();

        inquiryService.removeInquiry(inquiry_id);
    }
}
