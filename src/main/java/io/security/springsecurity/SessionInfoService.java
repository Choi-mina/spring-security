package io.security.springsecurity;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionInfoService {

    public SessionInfoService(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    private final SessionRegistry sessionRegistry;

   public void sessionInfo() {
       for(Object principal : sessionRegistry.getAllPrincipals()) {
           List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, false);
           for(SessionInformation session : allSessions) {
               System.out.println("사용자: " + principal + " 세션ID: " + session.getSessionId() + " 최종 요청 시간: " + session.getLastRequest());
           }
       }
   }
}
