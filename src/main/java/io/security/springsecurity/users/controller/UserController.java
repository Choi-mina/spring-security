package io.security.springsecurity.users.controller;

import io.security.springsecurity.domain.dto.AccountDto;
import io.security.springsecurity.domain.entity.Account;
import io.security.springsecurity.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public String signup(AccountDto accountDto) {
        // Dto -> Entity
        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        userService.coreateUser(account);

        return "redirect:/";
    }
}
