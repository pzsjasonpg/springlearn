package com.pzs.spring.aop.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(1)
@Aspect
@Component
public class ValidateAspect {

    @Before("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void validateArgs(JoinPoint joinPoint) {
        System.out.println(">> validate: " + Arrays.asList(joinPoint.getArgs()));
    }
}
