package com.palleteforco.palleteforco.domain.member.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private String email;
    private String name;
    private String phone_number;
    private LocalDateTime join_date;
}
