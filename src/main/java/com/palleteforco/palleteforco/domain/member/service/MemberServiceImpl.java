package com.palleteforco.palleteforco.domain.member.service;

import com.palleteforco.palleteforco.domain.google.dto.Role;
import com.palleteforco.palleteforco.domain.member.dto.MemberDto;
import com.palleteforco.palleteforco.domain.member.mapper.MemberMapper;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, OAuth2Service oAuth2Service) {
        this.memberMapper = memberMapper;
        this.oAuth2Service = oAuth2Service;
    }

    public void addMemberInfo(MemberDto memberDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        MemberDto existing = memberMapper.selectMemberProfile(memberDto.getEmail());

        if (existing == null) {
            memberMapper.insertMemberInfo(memberDto);
            oAuth2Service.updateRole(email, Role.MEMBER);
        }
    }

    public List<MemberDto> getGoogleMemberInfo() throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        return memberMapper.selectGoogleMemberInfo(email);
    }

    public void modifyMemberInfo(MemberDto memberDto) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        memberDto.setEmail(email);

        memberMapper.updateMemberInfo(memberDto);
    }
}
