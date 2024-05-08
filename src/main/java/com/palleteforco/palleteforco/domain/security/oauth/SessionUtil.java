package com.palleteforco.palleteforco.domain.security.oauth;

import com.palleteforco.palleteforco.domain.google.dto.GoogleDto;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUtil implements Serializable {
    private String email;

    public SessionUtil(GoogleDto googleDto) {
        this.email = googleDto.getEmail();
    }
}
