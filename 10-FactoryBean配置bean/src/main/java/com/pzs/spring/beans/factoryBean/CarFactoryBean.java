package com.pzs.spring.beans.factoryBean;

import org.springframework.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    //返回bean的对象
    public Car getObject() throws Exception {
        return new Car("BMW",500000);
    }

    //返回bean的类型
    public Class<Car> getObjectType() {
        return Car.class;
    }

    //是否单例
    public boolean isSingleton() {
        return true;
    }
}
