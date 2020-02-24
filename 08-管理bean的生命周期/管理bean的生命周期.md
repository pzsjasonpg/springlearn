## IOC容器中bean的生命周期方法

Spring IOC 容器可以管理 Bean 的生命周期, Spring 允许在 Bean 生命周期的特定点执行定制的任务. 
Spring IOC 容器对 Bean 的生命周期进行管理的过程:

- 通过构造器或工厂方法创建 Bean 实例
- 为 Bean 的属性设置值和对其他 Bean 的引用
- 调用 Bean 的初始化方法
- 当容器关闭时, 调用 Bean 的销毁方法

在 Bean 的声明里设置 init-method 和 destroy-method 属性, 为 Bean 指定初始化和销毁方法

bean类为：

```java
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
```

xml配置：

```xml
<bean id="car" class="com.pzs.spring.beans.cycle.Car"
          init-method="init"
          destroy-method="destroy">
        <property name="brand" value="Audi"></property>
</bean>
```

主方法：

```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-cycle.xml");
Car car = (Car) ctx.getBean("car");
System.out.println(car);
((ClassPathXmlApplicationContext) ctx).close();
```

最后结果输出为：

```Cat's constructor ...
Cat's constructor ...
init ...
com.pzs.spring.beans.cycle.Car@91161c7
destory ...
```



## 创建Bean后置处理器

Bean 后置处理器允许在调用初始化方法前后对 Bean 进行额外的处理.
Bean 后置处理器对 IOC 容器里的所有 Bean 实例逐一处理, 而非单一实例. 其典型应用是: 检查 Bean 属性的正确性或根据特定的标准更改 Bean 的属性.
对Bean 后置处理器而言, 需要实现**BeanPostProcessor**接口. 在初始化方法被调用前后, Spring 将把每个 Bean 实例分别传递给上述接口的以下两个方法:

- postProcessAfterInitialization
- postProcessBeforeInitialization

Spring IOC 容器对 Bean 的生命周期进行管理的过程:

- 通过构造器或工厂方法创建 Bean 实例
- 为 Bean 的属性设置值和对其他 Bean 的引用
- 将 Bean 实例传递给 Bean 后置处理器的 postProcessBeforeInitialization 方法
- 调用 Bean 的初始化方法
- 将 Bean 实例传递给 Bean 后置处理器的 postProcessAfterInitialization方法
- Bean 可以使用了
- 当容器关闭时, 调用 Bean 的销毁方法

```java
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     *
     * @param o bean对象
     * @param s bean名称
     * @return
     * @throws BeansException
     */
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessBeforeInitialization: " + o + " , " + s);
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessAfterInitialization: " + o + " , " + s);
        return o;
    }
}
```

配置bean后置处理器

```xml
<bean class="com.pzs.spring.beans.cycle.MyBeanPostProcessor"></bean>
```

输出结果：

```
Cat's constructor ...
postProcessBeforeInitialization: com.pzs.spring.beans.cycle.Car@77caeb3e , car
init ...
postProcessAfterInitialization: com.pzs.spring.beans.cycle.Car@77caeb3e , car
com.pzs.spring.beans.cycle.Car@77caeb3e
destory ...
```

结论：后置处理器在初始化方法前后处理功能。