package com.pzs.spring.aop.helloworld;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class CalculatorLoggingsProxy {
    private CalculatorInterace target;

    public CalculatorLoggingsProxy(CalculatorInterace target) {
        this.target = target;
    }

    public CalculatorInterace getLogging() {
        CalculatorInterace proxy = null;
        //proxy object have been loaded which ClassLoader
        ClassLoader loader = target.getClass().getClassLoader();
        //proxy object's type
        Class[] interfaces = new Class[]{CalculatorInterace.class};
        //invoking proxy objecct's method, and excuting myself function.
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String mehodName = method.getName();
                System.out.println("The method " + mehodName + " begins with " + Arrays.asList(args));
                Object result = method.invoke(target, args);
                System.out.println("The methos " + mehodName + " ends with " +result);
                return result;
            }
        };
        proxy = (CalculatorInterace) Proxy.newProxyInstance(loader, interfaces, h);
        return proxy;
    }
}
