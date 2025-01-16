package io.security.springsecurity.users.service;

import io.security.springsecurity.domain.entity.Account;
import io.security.springsecurity.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void coreateUser(Account account) {
        userRepository.save(account);
    }
}
