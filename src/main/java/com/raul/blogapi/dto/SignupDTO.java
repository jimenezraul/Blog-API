package com.raul.blogapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupDTO {
    private String name;
    private LocalDate birthDate;
    private String username;
    private String email;
    private String password;

}
