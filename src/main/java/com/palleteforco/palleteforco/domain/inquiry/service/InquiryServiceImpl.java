package com.palleteforco.palleteforco.domain.inquiry.service;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.mapper.InquiryMapper;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class InquiryServiceImpl implements InquiryService {
    private final InquiryMapper inquiryMapper;

    @Autowired
    public InquiryServiceImpl(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    // 문의사항 등록
    @Transactional
    public void registerInquiry(InquiryDto inquiryDto) throws Exception {
        inquiryDto.setInquiry_regdate(LocalDateTime.now());
        inquiryMapper.insertInquiry(inquiryDto);
    }

    // 문의사항 목록
    public List<InquiryDto> getInquiryList() throws Exception {
        return inquiryMapper.selectInquiryList();
    }

    // 문의사항 상세 보기
    @Transactional
    public InquiryDto getInquiryListDetail(Long inquiry_id) throws Exception {
        InquiryDto inquiryDto = inquiryMapper.selectInquiryListDetail(inquiry_id);

        if (inquiryDto == null) {
            throw new NotFoundException("찾으시는 게시물이 없습니다.");
        }

        inquiryDto.setInquiry_update_date(LocalDateTime.now());

        return inquiryMapper.selectInquiryListDetail(inquiry_id);
    }

    // 문의사항 수정
    @Transactional
    public void modifyInquiry(InquiryDto inquiryDto) throws Exception {
        InquiryDto existing = inquiryMapper.selectInquiryListDetail(inquiryDto.getInquiry_id());

        if (existing == null) {
            throw new NotFoundException("수정할 게시물이 없습니다.");
        }

        inquiryDto.setInquiry_update_date(LocalDateTime.now());

        inquiryMapper.updateInquiry(inquiryDto);
    }

    // 문의사항 삭제
    @Transactional
    public void removeInquiry(Long inquiry_id) throws Exception {
        InquiryDto existing = inquiryMapper.selectInquiryListDetail(inquiry_id);

        if (existing == null) {
            throw new NotFoundException("삭제할 게시물이 없습니다.");
        }

        inquiryMapper.deleteInquiry(inquiry_id);
    }
}
