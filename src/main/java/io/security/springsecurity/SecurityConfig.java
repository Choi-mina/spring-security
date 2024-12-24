package io.security.springsecurity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationEventPublisher eventPublisher;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user").hasAuthority("ROLE_USER")
//                        .requestMatchers("/db").hasAuthority("ROLE_DB")
//                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
//                        .anyRequest().permitAll())
//                .formLogin(form -> form.successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        eventPublisher.publishEvent(new CustomAuthenticationSuccessEvent(authentication));
//                        response.sendRedirect("/");
//                    }
//                }))
//                .formLogin(Customizer.withDefaults())
//                .authenticationProvider(customAuthenticationProvider())
//                .csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user").hasAuthority("ROLE_USER")
                        .requestMatchers("/db").hasAuthority("ROLE_DB")
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(customAuthenticationProvider());

        return http.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(customAuthenticationEventPublisher(null));
    }
    @Bean
    public AuthenticationEventPublisher customAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        Map<Class<? extends AuthenticationException>, Class<? extends AbstractAuthenticationFailureEvent>> mapping =
                Collections.singletonMap(CustomException.class, CustomAuthenticationFailureEvent.class);

        DefaultAuthenticationEventPublisher authenticationEventPublisher = new DefaultAuthenticationEventPublisher(applicationEventPublisher);
        authenticationEventPublisher.setAdditionalExceptionMappings(mapping); // CustomException 을 던지면 CustomAuthenticationFailureEvent 를 발행하도록 추가 함
        authenticationEventPublisher.setDefaultAuthenticationFailureEvent(DefaultAuthenticationFailureEvent.class);
        return authenticationEventPublisher;
    }
//
//    @Bean
//    public CustomAuthenticationProvider2 customAuthenticationProvider2() {
//        return new CustomAuthenticationProvider2(authenticationEventPublisher(null));
//    }
//
//    @Bean
//    public DefaultAuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher eventPublisher) {
//        DefaultAuthenticationEventPublisher publisher = new DefaultAuthenticationEventPublisher(eventPublisher);
//        return publisher;
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        UserDetails db = User.withUsername("db").password("{noop}1111").roles("DB").build();
        UserDetails admin = User.withUsername("admin").password("{noop}1111").roles("ADMIN", "SECURE").build();
        return new InMemoryUserDetailsManager(user, db, admin);
    }
}
