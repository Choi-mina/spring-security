package io.security.springsecurity;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    // 인터셉터, Advice 호출 -> 처리 후 해당 메소드 진입
    public String getUser() {
        return "user";
    }

    public Account getOwner(String name) {
        return new Account(name, false);
    }

    public String display() {
        return "display";
    }
}
