package com.pzs.spring.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 实例工厂方法创建实例，就是要先创建工厂类的bean，再获取car的bean实例。
 */
public class InstanceCarFactory {
    private Map<String,Car> car = null;

    public InstanceCarFactory() {
        car = new HashMap<String, Car>();
        car.put("Audi",new Car("Audi",400000));
        car.put("Ford",new Car("Ford",500000));
    }

    public Car getCar(String brand) {
        return car.get(brand);
    }
}
