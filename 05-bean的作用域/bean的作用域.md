- 在 Spring 中, 可以在 <bean> 元素的 **scope** 属性里设置 Bean 的作用域.

- **默认情况下, Spring只为每个在 IOC容器里声明的 Bean创建唯一一个实例,整个 IOC 容器范围内都能共享该实例**：所有后续的 getBean() 调用和 Bean 引用都将返回这个唯一的 Bean 实例.该作用域被称为 singleton, 它是所有 Bean 的默认作用域.



| 作用域      | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| singleton   | 在spring IoC容器仅存在一个Bean实例，Bean以单例方式存在，bean作用域范围的默认值。 |
| prototype   | 每次从容器中调用Bean时，都返回一个新的实例，即每次调用getBean()时，相当于执行newXxxBean()。 |
| request     | 每次HTTP请求都会创建一个新的Bean，该作用域仅适用于web的Spring WebApplicationContext环境。 |
| session     | 同一个HTTP Session共享一个Bean，不同Session使用不同的Bean。该作用域仅适用于web的Spring WebApplicationContext环境。 |
| application | 限定一个Bean的作用域为ServletContext的生命周期。该作用域仅适用于web的Spring WebApplicationContext环境。 |

 	

```xml
<bean id="car"
          class="com.pzs.beans.scope.Car"
          scope="prototype">
        <property name="brand" value="Baoma"></property>
        <property name="price" value="280000"></property>
    </bean>
```



```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-scope.xml");

        Car car = (Car) ctx.getBean("car");
        Car car2 = (Car) ctx.getBean("car");
        System.out.println(car == car2);
```

当作用域是默认的singleton，输出true。

作用域是prototype，输出false。





