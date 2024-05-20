package com.palleteforco.palleteforco.domain.inquiry.mapper;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    // 문의사항 등록
    public void insertInquiry(InquiryDto inquiryDto) throws Exception;

    // 문의사항 목록
    public List<InquiryDto> selectInquiryList() throws Exception;

    // 문의사항 상세 보기
    public InquiryDto selectInquiryListDetail(Long inquiry_id) throws Exception;

    // 문의사항 수정
    public void updateInquiry(InquiryDto inquiryDto) throws Exception;

    // 문의사항 삭제
    public void deleteInquiry(Long inquiry_id) throws Exception;

    // 문의사항 목록 마이페이지 조회
    public List<InquiryDto> selectMyInquiries(String email) throws Exception;
}
