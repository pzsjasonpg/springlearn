package com.pzs.spring.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态工厂方法：直接调用某一个类的静态方法就可以获取bean的实例
 */
public class StaticCarFactory {
    private static Map<String,Car> car = new HashMap<String, Car>();

    static {
        car.put("BaoMa", new Car("Baoma",300000));
        car.put("Audi", new Car("Audi",300000));
    }

    public static Car getCar(String name) {
        return car.get(name);
    }
}
