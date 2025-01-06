package io.security.springsecurity.sercurity.provider;

import io.security.springsecurity.domain.dto.AccountContext;
import io.security.springsecurity.sercurity.details.FormAuthenticationDetails;
import io.security.springsecurity.sercurity.exception.SecretException;
import io.security.springsecurity.sercurity.token.RestAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("restAuthenticationProvider")
@RequiredArgsConstructor
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String loginId = authentication.getName();
       String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId);

        if(!passwordEncoder.matches(password, accountContext.getPassword())) {  // 비밀번호가 일치하지 않는 경우
            throw new BadCredentialsException("Invalid password");
        }

        // 새로운 인증객체를 만들어 반환
        return new RestAuthenticationToken(accountContext.getAuthorities(), accountContext.getAccountDto(), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RestAuthenticationToken.class);
    }
}
