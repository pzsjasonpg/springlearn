## 1 创建bean 

```java
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
​```
```

## 2 创建spring xml配置文件

```xml
<?xml version="1.0" encoding="utf-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

    <!--    配置bean-->
    <bean id="helloWorld" class="com.pzs.beans.helloworld.HelloWorld">
        <property name="name2" value="Spring"></property>
    </bean>
</beans>
```



## 3 spring调用

```java
package com.pzs.beans.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldMain {
    public static void main(String[] args) {
        //创建对象和为属性赋值交给spring完成
        //1. 创建Spring的IOC容器对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2. 从IOC容器中获取Bean实例
        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");

        //3. 调用hello方法
        helloWorld.hello();
    }
}

```

