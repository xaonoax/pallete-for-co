package com.palleteforco.palleteforco.domain.member.service;

import com.palleteforco.palleteforco.domain.member.dto.MemberDto;

public interface MemberService {
    public void addMemberInfo(MemberDto memberDto) throws Exception;
    public MemberDto getMemberProfile(Long member_id) throws Exception;
    public void modifyMemberInfo(MemberDto memberDto) throws Exception;
}
