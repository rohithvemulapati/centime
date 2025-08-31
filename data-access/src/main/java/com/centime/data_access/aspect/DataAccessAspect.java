package com.centime.data_access.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class DataAccessAspect {

    @Before("@annotation(com.centime.data_access.annotations.LogMethodParam)")
    public void logMethodParams(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("Method: " + methodName + " called with params: " + Arrays.toString(args));
    }
}

