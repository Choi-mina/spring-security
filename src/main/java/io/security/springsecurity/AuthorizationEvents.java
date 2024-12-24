package io.security.springsecurity;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEvents {

    @EventListener
    public void onAuthorization(AuthorizationEvent event) { // 상위 이벤트 클래스는 하위 이벤트 클래스 호출 시 항상 호출
        System.out.println("event = " + event.getAuthentication().get().getAuthorities());
    }

    @EventListener
    public void onAuthorization(AuthorizationDeniedEvent failure) {
        System.out.println("failure = " + failure.getAuthentication().get().getAuthorities());
    }

    @EventListener
    public void onAuthorization(AuthorizationGrantedEvent success) {
        System.out.println("success = " + success.getAuthentication().get().getAuthorities());
    }
}
