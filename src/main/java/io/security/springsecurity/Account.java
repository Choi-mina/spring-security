package io.security.springsecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public class Account {
    private String owner;
    private boolean isSecure;
}
