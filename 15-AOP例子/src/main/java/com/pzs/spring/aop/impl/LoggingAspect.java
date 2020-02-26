package com.pzs.spring.aop.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//把这个类声明为一个切面：需要将其放置到IOC容器中，再声明为切面
@Aspect
@Component
public class LoggingAspect {

    //声明该方法是个前置通知，在目标方法之前执行
//    @Before("execution(public int com.pzs.spring.aop.impl.CalculatorInterace.add(int,int))")
    @Before("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("前置通知:The method " + methodName + " begins with " + args);
    }

    //后置通知：在目标方法执行后（无论是否发生异常都执行）
    //注意：后置通知不能访问目标方法返回的结果，需要在返回通知才能访问
    @After("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("后置通知：The method " + methodName + " ends");
    }

    /**
     * 方法正常结束受执行的代码
     * 返回通知是可以访问到方法的返回值
     */
    @AfterReturning(value = "execution(* com.pzs.spring.aop.impl.*.*(..))",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知：The method " + methodName + " ends with " + result);
    }

    /**
     * 目标方法出现异常时执行代码
     * 返回通知可以访问到异常对象，且可以指定在出现特定异常时执行通知代码
     */
    @AfterThrowing(value = "execution(* com.pzs.spring.aop.impl.*.*(..))", throwing = "ex")
    public void affterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知:he method " + methodName + " occurs exception: " + ex);
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint类型参数。
     * 环绕通知类似于动态代理全过程，ProceedingJoinPoint类型参数可以决定是否执行目标方法。
     * 且环绕方法必须有返回值。
     */
    @Around("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        try {
            System.out.println("环绕通知:The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            result = pjd.proceed();
            System.out.println("环绕通知：The method " + methodName + " ends with " + result);
        } catch (Throwable throwable) {
            System.out.println("环绕通知:he method " + methodName + " occurs exception: " + throwable);
            throw new RuntimeException(throwable);
        }
        System.out.println("环绕通知：The method " + methodName + " ends");
        return result;
    }

}
