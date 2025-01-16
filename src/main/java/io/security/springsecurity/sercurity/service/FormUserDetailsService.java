package io.security.springsecurity.sercurity.service;

import io.security.springsecurity.domain.dto.AccountContext;
import io.security.springsecurity.domain.dto.AccountDto;
import io.security.springsecurity.domain.entity.Account;
import io.security.springsecurity.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userRepository.findByUsername(username);
        if(account == null) {   // DB에 존재하지 X
            throw new UsernameNotFoundException("No user found with username" + username);
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));   // 권한 설정
        // Entity -> Dto
        ModelMapper mapper = new ModelMapper();
        AccountDto accountDto = mapper.map(account, AccountDto.class);

        // AccountContext는 UserDetails를 구현한 클래스로서 AccountDto를 Wrapping함
        return new AccountContext(accountDto, authorities);
    }
}
