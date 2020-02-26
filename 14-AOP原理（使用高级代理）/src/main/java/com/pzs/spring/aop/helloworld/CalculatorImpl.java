package com.pzs.spring.aop.helloworld;

public class CalculatorImpl implements CalculatorInterace {
    @Override
    public int add(int a, int b) {
        int result = a + b;
        return result;
    }

    @Override
    public int sub(int a, int b) {
        int result = a - b;
        return result;
    }

    @Override
    public int mul(int a, int b) {
        int result = a * b;
        return result;
    }

    @Override
    public int div(int a, int b) {
        int result = a / b;
        return result;
    }
}
