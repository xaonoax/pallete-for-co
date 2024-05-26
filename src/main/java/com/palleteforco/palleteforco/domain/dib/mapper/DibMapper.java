package com.palleteforco.palleteforco.domain.dib.mapper;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DibMapper {
    public void insertDib(DibDto dibDto) throws Exception;
    public DibDto selectDibById(Long dib_id) throws Exception;
    public void deleteDib(@Param("product_id") Long product_id, @Param("dib_id") Long dib_id) throws Exception;
    public List<DibDto> selectMyDib(String email) throws Exception;
    public DibDto selectDibByEmailProductId(@Param("email") String email, @Param("product_id") Long product_id) throws Exception;
}
