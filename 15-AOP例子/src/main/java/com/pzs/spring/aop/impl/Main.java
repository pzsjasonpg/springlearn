package com.pzs.spring.aop.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        //1. 创建IOC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2. 从IOC容器中获取bean的实例
        CalculatorInterace calculatorInterace = (CalculatorInterace) ctx.getBean(CalculatorInterace.class);

        //3. 使用bean
        int result = calculatorInterace.add(3, 6);
        System.out.println(result);

        result = calculatorInterace.mul(4, 7);
        System.out.println(result);

        result = calculatorInterace.div(1000, 0);
        System.out.println(result);
    }
}
