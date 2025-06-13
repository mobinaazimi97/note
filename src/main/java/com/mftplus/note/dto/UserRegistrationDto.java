package com.mftplus.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserRegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;

}
