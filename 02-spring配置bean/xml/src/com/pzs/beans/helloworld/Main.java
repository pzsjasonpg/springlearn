package com.pzs.beans.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        //创建对象和为属性赋值交给spring完成
        //1. 创建Spring的IOC容器对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

//        //2. 从IOC容器中获取Bean实例
//        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
//
//        //3. 调用hello方法
//        helloWorld.hello();

        Car car = (Car) ctx.getBean("car4");
        System.out.println(car);

        Person person = (Person) ctx.getBean("person4");
        System.out.println(person);

        PersonList personList = (PersonList) ctx.getBean("personList2");
        System.out.println(personList);

        PersonMap personMap = (PersonMap) ctx.getBean("personMap");
        System.out.println(personMap);

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        System.out.println(dataSource);
    }
}
