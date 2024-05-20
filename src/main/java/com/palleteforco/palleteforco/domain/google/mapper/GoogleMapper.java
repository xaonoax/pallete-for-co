package com.palleteforco.palleteforco.domain.google.mapper;

import com.palleteforco.palleteforco.domain.google.dto.GoogleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoogleMapper {
    public void insertGoogle(GoogleDto googleDto);

    public GoogleDto selectGoogleEmail(String email);

    public void updateGoogle(GoogleDto googleDto);
}
