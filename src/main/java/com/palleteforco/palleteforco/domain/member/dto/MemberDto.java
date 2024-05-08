package com.palleteforco.palleteforco.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private Long member_id;
    private String name;
    private String phone_number;
    private LocalDateTime join_date;
}
