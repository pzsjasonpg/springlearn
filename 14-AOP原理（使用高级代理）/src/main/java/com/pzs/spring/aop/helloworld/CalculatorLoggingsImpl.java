package com.pzs.spring.aop.helloworld;

public class CalculatorLoggingsImpl implements CalculatorInterace {
    @Override
    public int add(int a, int b) {
        System.out.println("The method add begins with [" + a + "," + b + "]");
        int result = a + b;
        System.out.println("The method add ends with " + result);
        return result;
    }

    @Override
    public int sub(int a, int b) {
        System.out.println("The method sub begins with [" + a + "," + b + "]");
        int result = a - b;
        System.out.println("The method sub ends with " + result);
        return result;
    }

    @Override
    public int mul(int a, int b) {
        System.out.println("The method mul begins with [" + a + "," + b + "]");
        int result = a * b;
        System.out.println("The method mul ends with " + result);
        return result;
    }

    @Override
    public int div(int a, int b) {
        System.out.println("The method div begins with [" + a + "," + b + "]");
        int result = a / b;
        System.out.println("The method div ends with " + result);
        return result;
    }
}
