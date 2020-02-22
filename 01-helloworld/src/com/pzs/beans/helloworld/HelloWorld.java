package com.pzs.beans.helloworld;

public class HelloWorld {

    private String name;

    public HelloWorld() {
        System.out.println("HelloWorld's constructor 被调用");
    }

    public void setName2(String name) { //name2用于在xml配置文件中的property名
        this.name = name;
    }

    public void hello() {
        System.out.println("hello: " + name);
    }
}
