package io.security.springsecurity.method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(prePostEnabled = false)
@Configuration
public class MethodSecurityConfig {

//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public Advisor preAuthorize() {
//        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize(new MyPreAuthorizationManager());
//    }
//
//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public Advisor postAuthorize() {
//        return AuthorizationManagerAfterMethodInterceptor.postAuthorize(new MyPostAuthorizationManager());
//    }

    // 포인트컷 -> 어노테이션 필요 X
    // 단일
//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public Advisor pointCutAdvisor() {
//        AspectJExpressionPointcut pattern = new AspectJExpressionPointcut();
//        pattern.setExpression("execution(* io.security.springsecurity.DataService.getUser(..))");
//        AuthorityAuthorizationManager<MethodInvocation> manager = AuthorityAuthorizationManager.hasRole("USER");
//
//        return new AuthorizationManagerBeforeMethodInterceptor(pattern, manager);
//    }
//
//    // 다중
//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public Advisor pointCutAdvisor2() {
//        AspectJExpressionPointcut pattern1 = new AspectJExpressionPointcut();
//        pattern1.setExpression("execution(* io.security.springsecurity.DataService.getUser(..))");
//
//        AspectJExpressionPointcut pattern2 = new AspectJExpressionPointcut();
//        pattern2.setExpression("execution(* io.security.springsecurity.DataService.getOwner(..))");
//
//        ComposablePointcut composablePointcut = new ComposablePointcut((Pointcut) pattern1);
//        composablePointcut.union((Pointcut) pattern2);
//
//        AuthorityAuthorizationManager<MethodInvocation> manager = AuthorityAuthorizationManager.hasRole("USER");
//
//        return new AuthorizationManagerBeforeMethodInterceptor(composablePointcut, manager);
//    }

    // AOP 초기화 과정
    // Advice
    @Bean
    public MethodInterceptor methodInterceptor () {
        AuthorizationManager<MethodInvocation> authorizationManager = new AuthenticatedAuthorizationManager<>();
        return new CustomMethodInterceptor(authorizationManager);
    }

    // Pointcut
    @Bean
    public Pointcut pointcut () {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* io.security.springsecurity.DataService.*(..))");
        return pointcut;
    }

    // Advisor
    @Bean
    public Advisor serviceAdvisor() {
        return new DefaultPointcutAdvisor(pointcut(), methodInterceptor());
    }
}
