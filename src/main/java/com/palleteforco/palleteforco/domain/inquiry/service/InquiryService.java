package com.palleteforco.palleteforco.domain.inquiry.service;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryResponse;

import java.util.List;

public interface InquiryService {
    // 문의사항 등록
    public InquiryResponse registerInquiry(InquiryDto inquiryDto) throws Exception;

    // 문의사항 목록
    public List<InquiryDto> getInquiryList() throws Exception;

    // 문의사항 상세 보기
    public InquiryDto getInquiryListDetail(Long inquiry_id) throws Exception;

    // 문의사항 수정
    public InquiryResponse modifyInquiry(InquiryDto inquiryDto) throws Exception;

    // 문의사항 삭제
    public void removeInquiry(Long inquiry_id) throws Exception;
}
