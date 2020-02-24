package com.pzs.spring.beans.cycle;

public class Car {
    private String brand;

    public Car() {
        System.out.println("Cat's constructor ...");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void init() {
        System.out.println("init ...");
    }

    public void destroy() {
        System.out.println("destory ...");
    }
}
