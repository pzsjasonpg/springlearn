## 在classpath中扫描组件

组件扫描(component scanning):  Spring 能够从 classpath 下自动扫描, 侦测和实例化具有特定注解的组件. 
		特定组件包括:

- @Component: 基本注解, 标识了一个受 Spring 管理的组件
- @Respository: 标识持久层组件
- @Service: 标识服务层(业务层)组件
- @Controller: 标识表现层组件

对于扫描到的组件, **Spring 有默认的命名策略**: 使用非限定类名, 第一个字母小写. **也可以在注解中通过 value 属性值标识组件的名称**

当在组件类上使用了特定的注解之后, 还需要在 Spring 的配置文件中声明``` <context:component-scan> ```：

- **base-package 属性指定一个需要扫描的基类包，Spring 容器将会扫描这个基类包里及其子包中的所有类.** 
- **当需要扫描多个包时, 可以使用逗号分隔.**如果仅希望扫描特定的类而非基包下的所有类，可使用 resource-pattern 属性过滤特定的类，示例：

```xml
<context:component-scan base-package="com.pzs.spring.beans.annonation" resource-pattern="repository/*.class">
</context:component-scan>
```

- ```<context:include-filter>``` 子节点表示要包含的目标类
- ```<context:exclude-filter> ```子节点表示要排除在外的目标类
- ```<context:component-scan> ```下可以拥有若干个 ```<context:include-filter>``` 和 ```<context:exclude-filter> ```子节点
- ```<context:include-filter>``` 和 ```<context:exclude-filter> ```子节点支持多种类型的过滤表达式：

![https://github.com/pzsjasonpg/springlearn/blob/master/11-%E9%80%9A%E8%BF%87%E6%B3%A8%E8%A7%A3%E9%85%8D%E7%BD%AEbean/filtertype.png](https://github.com/pzsjasonpg/springlearn/blob/master/11-%E9%80%9A%E8%BF%87%E6%B3%A8%E8%A7%A3%E9%85%8D%E7%BD%AEbean/filtertype.png)

### resource-pattern属性

resource-pattren属性指定仅希望扫描特定的类而非基包下的所有类。

### exclude-filter节点

类型为annonation时，expression中的包路径指的是spring的注解路径。

```xml
<!--不包含注解Repository的类-->
<context:component-scan base-package="com.pzs.spring.beans.annonation">
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"></context:exclude-filter>
</context:component-scan>
```



类型为assignable，该类型采用目标类是否继承或扩展某个特定类进行过滤。expression的路径是继承或或扩展某个特定类的全类名。

```xml
<!--指定排除目标类继承或扩展某个类-->
    <context:component-scan base-package="com.pzs.spring.beans.annonation">
        <context:exclude-filter type="assignable" expression="com.pzs.spring.beans.annonation.repository.UserRepository"></context:exclude-filter>
    </context:component-scan>
```





### include-filter节点

类型为annonation，指定包含扫描哪些注解过得的类。需要指定use-default-filters属性为"false"。

```xml
<!--指定只扫描某个注解过的类-->
    <context:component-scan base-package="com.pzs.spring.beans.annonation" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Repository"></context:include-filter>
    </context:component-scan>
```



类型为assignable,该类型采用目标类是否继承或扩展某个特定类进行过滤。需要指定use-default-filters属性为"false"。

```xml
<!--指定包含目标类继承或扩展某个类-->
    <context:component-scan base-package="com.pzs.spring.beans.annonation" use-default-filters="false">
        <context:include-filter type="assignable" expression="com.pzs.spring.beans.annonation.repository.UserRepository"></context:include-filter>
    </context:component-scan>
```

