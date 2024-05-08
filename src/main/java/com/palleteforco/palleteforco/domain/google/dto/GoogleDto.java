package com.palleteforco.palleteforco.domain.google.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoogleDto {
    private Long id;
    private String email;
    private Role role;

    @Builder
    public GoogleDto(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public GoogleDto update(String email) {
        this.email = email;

        return this;
    }
    public String getRoleKey() {
        return this.role.getKey();
    }

}
