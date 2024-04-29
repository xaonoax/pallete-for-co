package com.palleteforco.palleteforco.domain.dib.service;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.mapper.DibMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DibServiceImpl implements DibService{
    private final DibMapper dibMapper;

    @Autowired
    public DibServiceImpl(DibMapper dibMapper) {
        this.dibMapper = dibMapper;
    }

    public void addDib(DibDto dibDto) throws Exception {
        dibMapper.insertDib(dibDto);
    }

    public List<DibDto> getDibList() throws Exception {
        return dibMapper.selectDibList();
    }

    public void cancelDib(Long dib_id) throws Exception {
        dibMapper.deleteDib(dib_id);
    }
}
