package io.security.springsecurity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ServiceConfigurationError;

@RestController
public class IndexController {

    AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        return trustResolver.isAnonymous(authentication) ? "anonymous" : "authenticated";  // 인증 객체 타입이 anonymous인지 확인
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
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    // User객체의 필드를 가져올 수 있음
    @GetMapping("/user2")
    public String user2(@AuthenticationPrincipal(expression = "username") String user) {
        return user;
    }

    @GetMapping("/currentUser")
    public User currentUser(@CurrentUser User user) {
        return user;
    }

    @GetMapping("/currentUser2")
    public String currentUser2(@CurrentUsername String user) {
        return user;
    }

    @GetMapping("/db")
    public String db() {
        return "db";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

//    @GetMapping("/login")
//    public String login(HttpServletRequest request, MemberDto memberDto) throws ServletException {
//        request.login(memberDto.getUsername(), memberDto.getPassword());
//        System.out.println("login is Successsful");
//        return "login";
//    }

//    @GetMapping("/users")
//    public List<MemberDto> users(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        boolean authenticate = request.authenticate(response);
//        if(authenticate)
//            return List.of(new MemberDto("user", "1111"));
//        return Collections.emptyList();
//    }
}
