package io.security.springsecurity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.annotation.Documented;

@Data
@AllArgsConstructor
public class MemberDto {

    private String username;
    private String password;
}
