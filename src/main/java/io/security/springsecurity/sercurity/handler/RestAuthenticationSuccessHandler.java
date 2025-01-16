package io.security.springsecurity.sercurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.springsecurity.domain.dto.AccountDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("restSuccessHandler")
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        AccountDto accountDto = (AccountDto) authentication.getPrincipal();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        accountDto.setPassword(null);
        mapper.writeValue(response.getWriter(), accountDto);

        clearAuthenticationAttribute(request);
    }

    private void clearAuthenticationAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }

        // 인증에 성공하면 저장했던 예외를 세션으로부터 삭제함
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
