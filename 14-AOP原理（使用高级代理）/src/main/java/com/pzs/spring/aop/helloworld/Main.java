package com.pzs.spring.aop.helloworld;

public class Main {
    public static void main(String[] args) {
//        CalculatorInterace calculatorInterace = new CalculatorImpl();

        CalculatorInterace target = new CalculatorImpl();
        CalculatorInterace proxy = new CalculatorLoggingsProxy(target).getLogging();

        int result = proxy.add(1, 2);
        System.out.println("---> " + result);
        result = proxy.div(4,2);
        System.out.println("---> " + result);
    }
}
