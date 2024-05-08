package com.palleteforco.palleteforco.domain.google.domain;

import com.palleteforco.palleteforco.domain.google.dto.Role;
import lombok.Data;

@Data
public class Google {
    private Long id;
    private String email;
    private Role role;
}
