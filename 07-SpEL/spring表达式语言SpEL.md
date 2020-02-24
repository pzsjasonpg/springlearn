[TOC]



Spring 表达式语言（简称SpEL）：是一个支持运行时查询和操作对象图的强大的表达式语言。
语法类似于 EL：SpEL 使用 **#{…}** 作为定界符，所有在大框号中的字符都将被认为是 SpEL
SpEL 为 bean 的属性进行动态赋值提供了便利
通过 SpEL 可以实现：

- 通过 bean 的 id 对 bean 进行引用
- 调用方法以及引用对象中的属性
- 计算表达式的值
- 正则表达式的匹配

## 字面值

字面量的表示：

- 整数：```<property name="count" value="#{5}"/>```
- 小数：```<property name="frequency" value="#{89.7}"/>```
- 科学计数法：```<property name="capacity" value="#{1e4}"/>```
- String可以使用单引号或者双引号作为字符串的定界符号：```<property name="name" value="#{'Chuck'}"/>``` 或 ```<property name='name' value='#{"Chuck"}'/>```
- Boolean：```<property name="enabled" value="#{false}"/>```

```xml
<bean id="address" class="com.pzs.spring.beans.spel.Address">
        <property name="city" value="#{'BeiJing'}"></property>
        <property name="street" value="WuDaoKou"></property>
    </bean>
```



## 引用bean、属性和方法

```xml
<bean id="person" class="com.pzs.spring.beans.spel.Person">
        <property name="name" value="Tom"></property>
        <!--SpEL 引用其他对象-->
        <property name="car" value="#{car}"></property>
        <!--SpEL 引用其他对象的属性-->
        <property name="city" value="#{address.city}"></property>
        <!--SpEL 条件语句-->
        <property name="info" value="#{car.price > 300000 ? '金领' : '白领'}"></property>
    </bean>
```



### 引用静态属性

调用静态方法或静态属性：通过 T() 调用一个类的静态方法，它将返回一个 Class Object，然后再调用相应的方法或属性： 

```xml
<bean id="car" class="com.pzs.spring.beans.spel.Car">
        <property name="brand" value="Audi"></property>
        <property name="price" value="500000"></property>
        <!--SpEL 引用类的静态属性-->
        <property name="tyrePerimeter" value="#{T(java.lang.Math).PI * 80}"></property>
    </bean>
```





## SpEL支持的运算符号

- 算数运算符：+, -, *, /, %, ^：
- 加号还可以用作字符串连接：
- 比较运算符： <, >, ==, <=, >=, lt, gt, eq, le, ge
- 逻辑运算符号： and, or, not, |
- if-else 运算符：?: (ternary), ?: (Elvis)
- if-else 的变体
- 正则表达式：matches

