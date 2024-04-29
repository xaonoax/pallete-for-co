package com.palleteforco.palleteforco.domain.dib.mapper;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DibMapper {
    public void insertDib(DibDto dibDto) throws Exception;
    public List<DibDto> selectDibList() throws Exception;
    public void deleteDib(Long dib_id) throws Exception;
}
