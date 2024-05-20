package com.palleteforco.palleteforco.domain.member.service;

import com.palleteforco.palleteforco.domain.member.dto.MemberDto;

import java.util.List;

public interface MemberService {
    public void addMemberInfo(MemberDto memberDto) throws Exception;
    public List<MemberDto> getGoogleMemberInfo() throws Exception;
    public void modifyMemberInfo(MemberDto memberDto) throws Exception;
}
