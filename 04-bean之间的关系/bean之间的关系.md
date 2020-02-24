[TOC]



## 继承bean配置

- **Spring** **允许继承** **bean** **的配置**, 被继承的 bean 称为父 bean. 继承这个父 Bean 的 Bean 称为子 Bean

- **子 Bean从父 Bean 中继承配置,包括 Bean 的属性配置**

- 子 Bean 也可以**覆盖**从父 Bean 继承过来的配置

- 父 Bean 可以作为配置模板, 也可以作为 Bean 实例. **若只想把父 Bean作为模板, **可以设置```<bean>```的abstract **属性为** **true**, 这样 Spring 将不会实例化这个 Bean

- **并不是**```<bean>```**元素里的所有属性都会被继承**. 比如: autowire, abstract 等.

- 也**可以忽略父** **Bean** **的** **class** **属性**, 让子 Bean 指定自己的类, 而共享相同的属性配置. 但此时 **abstract** **必须设为** **true**

```xml
<!--    abstract属性设为true，spring不会实例化这个bean-->
    <bean id="address" class="com.pzs.beans.relation.Address" abstract="true">
        <property name="city" value="BeiJing"></property>
        <property name="street" value="HuiLongGuang"></property>
    </bean>

    <!--parent属性表示继承哪个bean，可以覆盖配置-->
    <bean id="address2" class="com.pzs.beans.relation.Address" parent="address">
        <property name="street" value="ZhongShan"></property>
    </bean>

    <!--    忽略class属性，必须将abstract属性设置为true-->
    <bean id="address3" abstract="true">
        <property name="city" value="ShangHai"></property>
        <property name="street" value="HuiLongGuang"></property>
    </bean>

    <bean id="address4" class="com.pzs.beans.relation.Address" parent="address3">
        <property name="street" value="ZhongShan"></property>
    </bean>
```



## 依赖bean配置

- **Spring** **允许用户通过** **depends-on** **属性设定** **Bean** **前置依赖的**Bean，前置依赖的 Bean 会在本 Bean 实例化之前创建好

- **如果前置依赖于多个** **Bean**，则可以通过逗号，空格或的方式配置 **Bean** **的名称**

```xml
<bean id="car" class="com.pzs.beans.relation.Car">
        <property name="brand" value="Baoma"></property>
        <property name="price" value="300000"></property>
    </bean>

    <bean id="person" class="com.pzs.beans.relation.Person" depends-on="car">
        <property name="name" value="Tom"></property>
        <property name="address" ref="address2"></property>
    </bean>
```

person依赖car这个bean，输出结果是

> Person{name='Tom', address=Address{city='BeiJing', street='ZhongShan'}, car=null}

关于为什么bean依赖了car，为什么输出是null？

> person只是依赖了car，但是并没有在person的bean中为car赋值，不写car的bean的时候抛出异常