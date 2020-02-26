package com.pzs.spring.aop.impl;

import com.pzs.spring.aop.helloworld.CalculatorInterace;

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
        //调用代理对象的方法
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String mehodName = method.getName();
                System.out.println("The method " + mehodName + " begins with " + Arrays.asList(args));
                Object result = null;
                try {
                    //前置通知
                    result = method.invoke(target, args);
                    //返回通知，可以访问到方法的返回值
                } catch (Exception e) {
                    e.printStackTrace();
                    //异常通知，可以访问到方法的异常
                    throw new RuntimeException(e);
                }
                //后置通知。因为方法可能会出异常，所以访问不到方法的返回值
                System.out.println("The methos " + mehodName + " ends with " +result);
                return result;
            }
        };
        proxy = (CalculatorInterace) Proxy.newProxyInstance(loader, interfaces, h);
        return proxy;
    }
}
