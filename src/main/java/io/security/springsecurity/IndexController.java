package io.security.springsecurity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/anonymous")
    public String anonymous() {
        return "anonymous";
    }

    @GetMapping("/authentication")
    public String authentication(Authentication authentication) {
        // 인증받지 않은 anonymous는 authentication 객체가 null로 들어옴
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        } else {
            return "null";
        }
    }

    @GetMapping("/anonymousContext")
    public String anonymousContext(@CurrentSecurityContext SecurityContext context) {
        // @CurrentSecurityContext를 사용하게 되면 anonymous를 객체로 받을 수 있음
        return context.getAuthentication().getName();
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccess() {
        return "logoutSuccess";
    }

    @GetMapping("/user")
    public String user() {return "user";}

    @GetMapping("/db")
    public String db() {return "db";}

    @GetMapping("/admin")
    public String admin() {return "admin";}

    @GetMapping("/secure")
    public String secure() {return "secure";}
}
