package com.palleteforco.palleteforco.domain.dib.service;

import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.mapper.DibMapper;
import com.palleteforco.palleteforco.domain.product.dto.ProductDto;
import com.palleteforco.palleteforco.domain.product.mapper.ProductMapper;
import com.palleteforco.palleteforco.domain.security.oauth.OAuth2Service;
import com.palleteforco.palleteforco.global.exception.ForbiddenExceptionHandler;
import com.palleteforco.palleteforco.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DibServiceImpl implements DibService{
    private final DibMapper dibMapper;
    private final ProductMapper productMapper;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public DibServiceImpl(DibMapper dibMapper,
                          ProductMapper productMapper,
                          OAuth2Service oAuth2Service) {
        this.dibMapper = dibMapper;
        this.productMapper = productMapper;
        this.oAuth2Service = oAuth2Service;
    }

    @Transactional
    public void addDib(DibDto dibDto) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        dibDto.setEmail(email);

        ProductDto productId = productMapper.selectProductListDetail(dibDto.getProduct_id());
        log.info("getProduct_id : " + productMapper.selectProductListDetail(dibDto.getProduct_id()));

        if (productId == null) {
            throw new NotFoundException("해당 제품이 존재하지 않습니다.");
        }

        DibDto existingEmailAndProductId = dibMapper.selectDibByEmailProductId(email, dibDto.getProduct_id());

        log.info("addDib existing : " + existingEmailAndProductId);

        if (existingEmailAndProductId != null) {
            throw new NotFoundException("이미 찜한 제품입니다.");
        }

        dibMapper.insertDib(dibDto);
    }

    @Transactional
    public void cancelDib(Long product_id, Long dib_id) throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();
        DibDto existing = dibMapper.selectDibById(dib_id);

        if (existing == null || !existing.getProduct_id().equals(product_id)) {
            throw new NotFoundException("찜한 제품이 없습니다.");
        }

        if (!existing.getEmail().equals(email)) {
            throw new ForbiddenExceptionHandler("접근 권한이 없습니다.");
        }

        dibMapper.deleteDib(product_id, dib_id);
    }

    @Transactional
    public List<DibDto> getMyDib() throws Exception {
        String email = oAuth2Service.getPrincipalMemberEmail();

        return dibMapper.selectMyDib(email);
    }

}
