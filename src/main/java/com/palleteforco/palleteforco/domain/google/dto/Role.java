package com.palleteforco.palleteforco.domain.google.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    MEMBER("ROLE_MEMBER", "회원");

    private final String key;
    private final String title;
}
