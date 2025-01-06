package io.security.springsecurity.sercurity.handler;

import io.security.springsecurity.sercurity.exception.SecretException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid username or password";

        if(exception instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password";
        } else if(exception instanceof UsernameNotFoundException) {
            errorMessage = "Username not found";
        } else if(exception instanceof CredentialsExpiredException) {
            errorMessage = "User credentials expired";
        } else if(exception instanceof SecretException) {
            errorMessage = "Secret exception";
        }

        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
