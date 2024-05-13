package com.palleteforco.palleteforco.domain.dib.service;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.mapper.DibMapper;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class DibServiceImpl implements DibService{
    private final DibMapper dibMapper;

    @Autowired
    public DibServiceImpl(DibMapper dibMapper) {
        this.dibMapper = dibMapper;
    }

    public void addDib(DibDto dibDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        dibDto.setEmail(email);

        dibMapper.insertDib(dibDto);
    }

    public void cancelDib(Long dib_id) throws Exception {
        DibDto existing = dibMapper.selectDib(dib_id);

        if (existing == null) {
            throw new NotFoundException("좋아요 한 게시물이 없습니다.");
        }

        dibMapper.deleteDib(dib_id);
    }
}
