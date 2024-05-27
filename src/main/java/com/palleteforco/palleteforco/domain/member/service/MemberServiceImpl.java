package com.palleteforco.palleteforco.domain.member.service;

import com.palleteforco.palleteforco.domain.google.dto.Role;
import com.palleteforco.palleteforco.domain.member.dto.MemberDto;
import com.palleteforco.palleteforco.domain.member.mapper.MemberMapper;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Transactional
    public void addMemberInfo(MemberDto memberDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        MemberDto existing = memberMapper.selectMemberProfile(memberDto.getEmail());

        memberDto.setEmail(email);
        memberDto.setJoin_date(LocalDateTime.now());

        if (existing == null) {
            memberMapper.insertMemberInfo(memberDto);
            oAuth2Service.updateRole(email, Role.MEMBER);
        }
    }

    // --- 마이페이지에서 사용 ---
    @Transactional
    public void modifyMemberInfo(MemberDto memberDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        memberDto.setEmail(email);

        memberMapper.updateMemberInfo(memberDto);
    }

    @Transactional
    public List<MemberDto> getGoogleMemberInfo() throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        return memberMapper.selectGoogleMemberInfo(email);
    }
}
