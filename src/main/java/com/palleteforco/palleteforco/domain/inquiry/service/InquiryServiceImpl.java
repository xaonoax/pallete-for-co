
package com.palleteforco.palleteforco.domain.inquiry.service;

import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryResponse;
import com.palleteforco.palleteforco.domain.inquiry.mapper.InquiryMapper;
import com.palleteforco.palleteforco.domain.member.mapper.MemberMapper;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class InquiryServiceImpl implements InquiryService {
    private final InquiryMapper inquiryMapper;
    private final MemberMapper memberMapper;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public InquiryServiceImpl(InquiryMapper inquiryMapper,
                              MemberMapper memberMapper,
                              OAuth2Service oAuth2Service) {
        this.inquiryMapper = inquiryMapper;
        this.memberMapper = memberMapper;
        this.oAuth2Service = oAuth2Service;
    }

    @Value("${file.upload.dir}")
    private String fileUploadDir;

    // 문의사항 등록
    @Transactional
    public InquiryResponse registerInquiry(InquiryDto inquiryDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        String writer = memberMapper.selectByName(email);

        log.info("***** 파일 첨부 ***** : " + inquiryDto.getInquiryFile());

        inquiryDto.setEmail(email);
        inquiryDto.setInquiry_reg_date(LocalDateTime.now());
        inquiryDto.setInquiry_writer(writer);

        InquiryResponse response = new InquiryResponse();

        if (inquiryDto.getInquiryFile() == null || inquiryDto.getInquiryFile().isEmpty()) {
            inquiryMapper.insertInquiry(inquiryDto);
        } else {
            MultipartFile inquiryFile = inquiryDto.getInquiryFile();
            String inquiryFileName = inquiryFile.getOriginalFilename();
            String inquiryStoredFileName = UUID.randomUUID() + "_" + inquiryFileName;

            // 파일 정보 등록
            inquiryDto.setInquiry_id(inquiryDto.getInquiry_id());
            inquiryDto.setInquiry_file_name(inquiryFileName);
            inquiryDto.setInquiry_stored_file_name(inquiryStoredFileName);

            String filePath = fileUploadDir + inquiryStoredFileName;
            inquiryFile.transferTo(new File(filePath));

            // 응답 객체에 파일 정보 설정하기
            response.setInquiry_file_name(inquiryFileName);
            response.setInquiry_stored_file_name(inquiryStoredFileName);

            inquiryMapper.insertInquiry(inquiryDto);
        }

        BeanUtils.copyProperties(inquiryDto, response);

        return response;
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
            throw new NotFoundException("해당 게시물이 없습니다.");
        }

        inquiryDto.setInquiry_update_date(LocalDateTime.now());

        return inquiryMapper.selectInquiryListDetail(inquiry_id);
    }

    // 문의사항 수정
    @Transactional
    public InquiryResponse modifyInquiry(InquiryDto inquiryDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        String writer = memberMapper.selectByName(email);
        InquiryDto existing = inquiryMapper.selectInquiryListDetail(inquiryDto.getInquiry_id());

        inquiryDto.setEmail(email);
        inquiryDto.setInquiry_update_date(LocalDateTime.now());
        inquiryDto.setInquiry_writer(writer);

        if (existing == null) {
            throw new NotFoundException("수정할 게시물이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        inquiryDto.setInquiry_update_date(LocalDateTime.now());

        log.info("***** 파일 첨부 ***** " +
                ": " + inquiryDto.getInquiryFile());

        InquiryResponse response = new InquiryResponse();

        if (inquiryDto.getInquiryFile() == null || inquiryDto.getInquiryFile().isEmpty()) {
            inquiryMapper.updateInquiry(inquiryDto);
        } else {
            MultipartFile inquiryFile = inquiryDto.getInquiryFile();
            String inquiryFileName = inquiryFile.getOriginalFilename();
            String inquiryStoredFileName = UUID.randomUUID() + "_" + inquiryFileName;

            inquiryDto.setInquiry_file_name(inquiryFileName);
            inquiryDto.setInquiry_stored_file_name(inquiryStoredFileName);
            inquiryDto.setInquiry_id(inquiryDto.getInquiry_id());

            String filePath = fileUploadDir + inquiryStoredFileName;
            inquiryFile.transferTo(new File(filePath));

            response.setInquiry_file_name(inquiryFileName);
            response.setInquiry_stored_file_name(inquiryStoredFileName);

            inquiryMapper.updateInquiry(inquiryDto);
        }

        BeanUtils.copyProperties(inquiryDto, response);

        return response;

    }

    // 문의사항 삭제
    @Transactional
    public void removeInquiry(Long inquiry_id) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        InquiryDto existing = inquiryMapper.selectInquiryListDetail(inquiry_id);

        if (existing == null) {
            throw new NotFoundException("삭제할 게시물이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        inquiryMapper.deleteInquiry(inquiry_id);
    }

    // 문의사항 목록 마이페이지 조회
    @Transactional
    public List<InquiryDto> getMyInquiries() throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        return inquiryMapper.selectMyInquiries(email);
    }
}

