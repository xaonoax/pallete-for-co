package com.palleteforco.palleteforco.domain.inquiry.controller;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryResponse;
import com.palleteforco.palleteforco.domain.inquiry.service.InquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/inquiries")
@Slf4j
public class InquiryController {
    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public InquiryResponse registerInquiry(@RequestPart("inquiryDto") InquiryDto inquiryDto,
                                           @RequestPart(value = "inquiryFile", required = false) MultipartFile inquiryFile) throws Exception {
        log.info("----------register OK---------");

        inquiryDto.setInquiryFile(inquiryFile);
        InquiryResponse response = inquiryService.registerInquiry(inquiryDto);

        return response;
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
    public InquiryResponse modifyInquiry(@PathVariable("inquiry_id") Long inquiry_id,
                                    @RequestPart("inquiryDto") InquiryDto inquiryDto,
                                    @RequestPart(value = "inquiryFile", required = false) MultipartFile inquiryFile) throws Exception {
        log.info("----------modify inquiry OK---------");

        inquiryDto.setInquiry_id(inquiry_id);
        inquiryDto.setInquiryFile(inquiryFile);
        InquiryResponse response = inquiryService.modifyInquiry(inquiryDto);

        return response;
    }

    @DeleteMapping("/{inquiry_id}")
    public void removeInquiry(@PathVariable("inquiry_id") Long inquiry_id) throws Exception {
        log.info("----------remove inquiry OK---------");

        InquiryDto inquiryDto = new InquiryDto();

        inquiryService.removeInquiry(inquiry_id);
    }
}
