package com.pzs.beans.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldMain {
    public static void main(String[] args) {
        //创建对象和为属性赋值交给spring完成
        //1. 创建Spring的IOC容器对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2. 从IOC容器中获取Bean实例
        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");

        //3. 调用hello方法
        helloWorld.hello();
    }
}
