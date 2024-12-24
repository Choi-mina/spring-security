package io.security.springsecurity;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {
    // 스프링의 이벤트 리스닝 메커니즘은 자바의 클래스 상속 구조를 따르기 때문에 특정 이벤트의 리스너는 해당 이벤트 뿐만 아니라 그 이벤트의 부모 클래스들로 부터 발생하는 이벤트도 처리할 수 있다.
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {     // 상위 이벤트
        System.out.println("success = " + success.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {    // 상위 이벤트
        System.out.println("failures = " + failures.getException().getMessage());
    }

    @EventListener
    public void onSuccess(InteractiveAuthenticationSuccessEvent success) {
        System.out.println("success = " + success.getAuthentication().getName());
    }

    @EventListener
    public void onSuccess(CustomAuthenticationSuccessEvent success) {
        System.out.println("success = " + success.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent failures) {
        System.out.println("failures = " + failures.getException().getMessage());
    }

    @EventListener
    public void onFailure(AuthenticationFailureProviderNotFoundEvent failures) {
        System.out.println("failures = " + failures.getException().getMessage());
    }

    @EventListener
    public void onFailure(CustomAuthenticationFailureEvent failures) {
        System.out.println("failures = " + failures.getException().getMessage());
    }
}
