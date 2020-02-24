Spring 中有两种类型的 Bean, 一种是普通Bean, 另一种是工厂Bean, 即FactoryBean. 
		工厂 Bean 跟普通Bean不同, 其返回的对象不是指定类的一个实例, 其返回的是该工厂 Bean 的 getObject 方法所返回的对象 

```java
public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
```



实现的Car的FactroyBean:

```java
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
```

xml配置：

```xml
<bean id="car" class="com.pzs.spring.beans.factoryBean.CarFactoryBean">
        <property name="brand" value="BMW"></property>
</bean>
```

