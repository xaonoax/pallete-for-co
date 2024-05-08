package com.palleteforco.palleteforco.domain.member.controller;

import com.palleteforco.palleteforco.domain.google.dto.GoogleDto;
import com.palleteforco.palleteforco.domain.member.dto.MemberDto;
import com.palleteforco.palleteforco.domain.member.service.MemberService;
import com.palleteforco.palleteforco.domain.security.oauth.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/members")
@Slf4j
public class MemberContoller {
    private final MemberService memberService;

    @Autowired
    public MemberContoller(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/join")
    public String getJson(Authentication authentication) {
        OAuth2User oAuth2User =(OAuth2User) authentication.getPrincipal();
        SessionUtil sessionUtil = new SessionUtil(GoogleDto.builder().build());
        Map<String, Object> attributes = oAuth2User.getAttributes();


        return attributes.toString();
    }

    @PostMapping("/join")
    public MemberDto addMemberInfo(@RequestBody MemberDto memberDto) throws Exception {
        log.info("----------join OK---------");
        memberService.addMemberInfo(memberDto);

        return memberDto;
    }
}
