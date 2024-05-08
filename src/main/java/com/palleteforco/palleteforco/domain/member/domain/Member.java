package com.palleteforco.palleteforco.domain.member.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private Long id;
    private Long member_id;
    private String name;
    private String phone_number;
    private LocalDateTime join_date;
}
