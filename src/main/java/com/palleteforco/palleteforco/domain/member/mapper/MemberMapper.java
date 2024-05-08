package com.palleteforco.palleteforco.domain.member.mapper;

import com.palleteforco.palleteforco.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public void insertMemberInfo(MemberDto memberDto) throws Exception;
    public MemberDto selectMemberProfile(Long id) throws Exception;
    public void updateMemberInfo(MemberDto memberDto) throws Exception;
}
