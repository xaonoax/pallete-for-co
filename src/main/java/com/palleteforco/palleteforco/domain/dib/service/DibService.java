package com.palleteforco.palleteforco.domain.dib.service;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;

import java.util.List;

public interface DibService {
    public void addDib(DibDto dibDto) throws Exception;
    public void cancelDib(Long dib_id) throws Exception;
    public List<DibDto> getMyDib() throws Exception;
}
