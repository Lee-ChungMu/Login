package com.kosa.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MemberRequestDTO {
    private String email;
    private String password;
    private String name;
}
