package com.palleteforco.palleteforco.domain.dib.mapper;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DibMapper {
    public void insertDib(DibDto dibDto) throws Exception;
    public DibDto selectDib(Long dib_id) throws Exception;
    public void deleteDib(Long dib_id) throws Exception;
}
