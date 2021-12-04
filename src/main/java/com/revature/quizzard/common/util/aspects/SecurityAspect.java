package com.revature.quizzard.common.util.aspects;

import com.revature.quizzard.common.exceptions.AuthenticationException;
import com.revature.quizzard.common.exceptions.AuthorizationException;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.common.util.web.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class SecurityAspect {

    private HttpServletRequest request;

    @Autowired
    public SecurityAspect(HttpServletRequest request) {
        this.request = request;
    }


    @Around("@annotation(com.revature.quizzard.common.util.web.Secured)")
    public Object secureEndpoints(ProceedingJoinPoint pjp) throws Throwable {

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured anno = method.getAnnotation(Secured.class);

        List<String> allowedUsers = Arrays.asList(anno.allowedUsers());
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new AuthenticationException("No session found.");
        }

        AppUser requestingUser = (AppUser) session.getAttribute("authUser");
        String username = requestingUser.getUsername();
        if (!allowedUsers.contains(username)) {
            throw new AuthorizationException("Forbidden request made to sensitive endpoint by user: " + username);
        }

        Object returned = pjp.proceed();

        System.out.println("Sensitive endpoint request serviced at " + LocalDateTime.now());

        return returned;
    }
}
