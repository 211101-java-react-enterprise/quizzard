package com.revature.quizzard.common.util.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger();

    @Pointcut("within(com.revature..*)")
    public void logAll() {}

    @Before("logAll()") // Pointcut expression language
    public void logMethodStart(JoinPoint jp) {

        // JoinPoint object represents the point in which we are weaving/injecting this advice logic
        String methodSig = extractMethodSignature(jp);
        String methodArguments = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {} with provided arguments: {}", methodSig, LocalDateTime.now(), methodArguments);

    }

    @AfterReturning(pointcut = "logAll()", returning = "returnedObj")
    public void logMethodReturn(JoinPoint jp, Object returnedObj) {
        String methodSig = extractMethodSignature(jp);
        logger.info("{} successfully returned at {} with value: {}", methodSig, LocalDateTime.now(), returnedObj);
    }

    @AfterThrowing(pointcut = "logAll()", throwing = "t")
    public void logMethodException(JoinPoint jp, Throwable t) {
        String methodSig = extractMethodSignature(jp);
        String exceptionName = t.getClass().getName();
        logger.info("{} was thrown in method {} at {} with message: {}", exceptionName, methodSig, LocalDateTime.now(), t.getMessage());
    }

    private String extractMethodSignature(JoinPoint jp) {
        return jp.getTarget().getClass().toString() + "#" + jp.getSignature().getName();
    }

}
