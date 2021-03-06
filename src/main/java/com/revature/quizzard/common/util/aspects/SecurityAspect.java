package com.revature.quizzard.common.util.aspects;

import com.revature.quizzard.auth.TokenService;
import com.revature.quizzard.common.exceptions.AuthenticationException;
import com.revature.quizzard.common.exceptions.AuthorizationException;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.common.util.web.Secured;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class SecurityAspect {

    private final TokenService tokenService;

    @Autowired
    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Order(1)
    @Before("@annotation(com.revature.quizzard.common.util.web.Authenticated)")
    public void requireAuthentication() {
        if (!sessionExists()) throw new AuthenticationException("No session token found.");
    }

    // TODO consider refactor to allow owner of all resources to be easily and consistently accessed
//    @Order(2)
//    @Before("@annotation(com.revature.quizzard.common.util.web.RequesterOwned)")
//    public void requireProofOwnershipOrAuthority(JoinPoint jp) {
//        HttpSession session = getCurrentSessionIfExists().orElseThrow(() -> new AuthenticationException("No session found."));
//        EditResourceRequest requestBody = (EditResourceRequest) jp.getArgs()[0];
//
//        AppUser requestingUser = (AppUser) session.getAttribute("authUser");
//        AppUser.AccountType requesterRole = requestingUser.getAccountType();
//
//        boolean isAdminOrDev = requesterRole.equals(AppUser.AccountType.ADMIN) || requesterRole.equals(AppUser.AccountType.DEV);
//        boolean requesterEditSelf = requestingUser.getId().equals(requestBody.getId());
//
//        if (!requesterEditSelf && !isAdminOrDev) {
//            throw new AuthorizationException("Forbidden request made.");
//        }
//    }

//    @Order(3)
//    @Before("@annotation(com.revature.quizzard.common.util.web.Secured)")
//    public void secureEndpoints(JoinPoint jp) {
//
//        Secured annotation = getAnnotationFromJoinPoint(jp, Secured.class);
//        List<AppUser.AccountType> allowedUsers = Arrays.asList(annotation.allowedAccountTypes());
//
//        HttpSession session = getCurrentSessionIfExists().orElseThrow(() -> new AuthenticationException("No session found."));
//        AppUser requester = ((AppUser) session.getAttribute("authUser"));
//        AppUser.AccountType requesterAccountType = requester.getAccountType();
//
//        if (!allowedUsers.contains(requesterAccountType)) {
//            throw new AuthorizationException("Forbidden request made to sensitive endpoint by user: " + requester.getUsername());
//        }
//
//    }

    private <T extends Annotation> T getAnnotationFromJoinPoint(JoinPoint jp, Class<T> annotationType) {
        return ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(annotationType);
    }

    private boolean sessionExists() {
        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader("Authorization");
        return tokenService.isTokenValid(token);
    }

}
