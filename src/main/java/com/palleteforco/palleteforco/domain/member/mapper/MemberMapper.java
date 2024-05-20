package com.palleteforco.palleteforco.domain.member.mapper;

import com.palleteforco.palleteforco.domain.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    public void insertMemberInfo(MemberDto memberDto) throws Exception;
    public MemberDto selectMemberProfile(String email) throws Exception;
    public List<MemberDto> selectGoogleMemberInfo(String email) throws Exception;
    public void updateMemberInfo(MemberDto memberDto) throws Exception;
}
