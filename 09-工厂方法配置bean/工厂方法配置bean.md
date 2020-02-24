[TOC]

## 调用静态工厂方法创建bean

调用静态工厂方法创建 Bean是将对象创建的过程封装到静态方法中. 当客户端需要对象时, 只需要简单地调用静态方法, 而不同关心创建对象的细节.
		要声明通过静态方法创建的 Bean, 需要在 Bean 的 class 属性里指定拥有该工厂的方法的类, 同时在 factory-method 属性里指定工厂方法的名称. 最后, 使用 ```<constrctor-arg>``` 元素为该方法传递方法参数.

Car类代码：

```java
public class Car {
    private String brand;
    private double price;

    public Car() {
        System.out.println("Cat's constructor ...");
    }

    public Car(String brand, double price) {
        this.brand = brand;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
```

静态工厂类代码：

```java
**
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
```

xml配置：

```xml
<!--
        class属性：指向静态工厂方法的全类名
        factory-method: 指向静态工厂方法的名字
        constructor-arg: 如果工厂方法需要传入参数，则使用constructor-arg来配置参数
    -->
    <bean id="car" class="com.pzs.spring.beans.factory.StaticCarFactory" factory-method="getCar">
        <constructor-arg value="BaoMa"></constructor-arg>
    </bean>
```



## 调用实例工厂方法创建bean

实例工厂代码：

```java
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
```

xml配置：

```xml
<!--配置工厂的实例-->
    <bean id="carFactory" class="com.pzs.spring.beans.factory.InstanceCarFactory"></bean>

    <bean id="car2" factory-bean="carFactory" factory-method="getCar">
        <constructor-arg value="Ford"></constructor-arg>
    </bean>
```

