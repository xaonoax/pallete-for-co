package com.palleteforco.palleteforco.domain.dib.service;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;

public interface DibService {
    public void addDib(DibDto dibDto) throws Exception;
    public void cancelDib(Long dib_id) throws Exception;
}
