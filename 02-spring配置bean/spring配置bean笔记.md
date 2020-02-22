## id：Bean 的名称。

–**在 IOC 容器中必须是唯一的**

–**若 id 没有指定，Spring 自动将权限定性类名作为Bean的名字**

–id 可以指定多个名字，名字之间可用逗号、分号、或空格分隔



## Spring 提供了两种类型的 IOC 容器实现.

–**BeanFactory**: IOC 容器的基本实现.

–**ApplicationContext**: 提供了更多的高级特性. 是 BeanFactory 的子接口.

–BeanFactory 是 Spring 框架的基础设施，面向 Spring 本身；ApplicationContext 面向使用 Spring 框架的开发者，**几乎所有的应用场合都直接使用** **ApplicationContext** **而非底层的** **BeanFactory**

–无论使用何种方式, 配置文件时相同的.



## ApplicationContext 的主要实现类：

–**ClassPathXmlApplicationContext**：从 **类路径下**加载配置文件

–FileSystemXmlApplicationContext: 从文件系统中加载配置文件

•ConfigurableApplicationContext 扩展于 ApplicationContext，新增加两个主要方法：refresh() 和 **close()**， 让 ApplicationContext 具有启动、刷新和关闭上下文的能力

•**ApplicationContext** **在初始化上下文时就实例化所有单例的** **Bean**。

•WebApplicationContext 是专门为 WEB 应用而准备的，它允许从相对于 WEB 根目录的路径中完成初始化工作





## Spring 支持 3 种依赖注入的方式

–**属性注入**

–**构造器注入**

–工厂方法注入（很少使用，不推荐）



### 1 属性注入

- 属性注入即通过 **setter** **方法**注入Bean 的属性值或依赖的对象

- 属性注入使用 ```<property>``` 元素, 使用 name 属性指定 Bean 的属性名称，value 属性或``` <value> ```子节点指定属性值

- **属性注入是实际应用中最常用的注入方式**

```xml
	<bean id="helloWorld" class="com.pzs.beans.helloworld.HelloWorld">
        <property name="name2" value="Spring"></property>
    </bean>
```



### 2 构造方法注入

- 通过构造方法注入Bean 的属性值或依赖的对象，它保证了 Bean 实例在实例化后就可以使用。

- 构造器注入在 ```<constructor-arg> ```元素里声明属性, ```<constructor-arg> ```中***没有 name属性***

```xml
	<bean id="car" class="com.pzs.beans.helloworld.Car">
        <constructor-arg value="Baoma"></constructor-arg>
        <constructor-arg value="Baoma"></constructor-arg>
        <constructor-arg value="200"></constructor-arg>
        <constructor-arg value="300000"></constructor-arg>
    </bean>
```

==使用构造方法注入参数值可以指定参数的位置和参数的类型！以区分重载的构造器==

bean中构造器为：

```java
	public Car(String company, String brand, int maxSpeed, float price) {
		super();
		this.company = company;
		this.brand = brand;
		this.maxSpeed = maxSpeed;
		this.price = price;
	}
	public Car(String company, String brand, float price) {
		super();
		this.company = company;
		this.brand = brand;
		this.price = price;
	}

	public Car(String company, String brand, int maxSpeed) {
		super();
		this.company = company;
		this.brand = brand;
		this.maxSpeed = maxSpeed;
	}
```

xml配置文件为：

```xml
	<bean id="car" class="com.pzs.beans.helloworld.Car">
        <constructor-arg value="Baoma" index="0"></constructor-arg>
        <constructor-arg value="Baoma" index="1"></constructor-arg>
        <constructor-arg value="200" index="2"></constructor-arg>
        <constructor-arg value="300000" index="3"></constructor-arg>
    </bean>

    <bean id="car2" class="com.pzs.beans.helloworld.Car">
        <constructor-arg value="Baoma"></constructor-arg>
        <constructor-arg value="Baoma"></constructor-arg>
        <constructor-arg value="300000" type="float"></constructor-arg>
    </bean>

    <bean id="car3" class="com.pzs.beans.helloworld.Car">
        <constructor-arg value="Baoma"></constructor-arg>
        <constructor-arg value="Baoma"></constructor-arg>
        <constructor-arg value="200" type="int"></constructor-arg>
    </bean>
```



## 属性配置细节

### 1 字面值

- 字面值：可用字符串表示的值，可以通过 ```<value>``` 元素标签或 value 属性进行注入。

- 基本数据类型及其封装类、String 等类型都可以采取字面值注入的方式

- 若字面值中包含特殊字符，可以使用 ```<![CDATA[]]>``` 把字面值包裹起来。

```xml
    <bean id="car4" class="com.pzs.beans.helloworld.Car">
        <constructor-arg value="Baoma" type="java.lang.String"></constructor-arg>
        <constructor-arg type="java.lang.String">
            <value><![CDATA[<ShangHai^>]]></value>
        </constructor-arg>
        <constructor-arg type="int">
            <value>200</value>
        </constructor-arg>
    </bean>
```



### 2 引用其他bean

- 组成应用程序的 Bean 经常需要相互协作以完成应用程序的功能. 要**使** **Bean** **能够相互访问**, 就必须在 Bean 配置文件中指定对 Bean 的引用

- 在 Bean 的配置文件中, 可以通过元素 **ref** **属性**为 Bean 的属性或构造器参数指定对 Bean 的引用.

- 也可以**在属性或构造器里包含** **Bean** **的声明**, 这样的 Bean 称为**内部** **Bean**

```xml
<bean id="person" class="com.pzs.beans.helloworld.Person">
        <property name="name" value="Tom"></property>
        <property name="age" value="24"></property>
        <property name="car" ref="car"></property>
    </bean>
```

#### 内部bean

- 当 Bean 实例**仅仅**给一个特定的属性使用时, 可以将其声明为内部 Bean. 内部 Bean 声明直接包含在 ```<property>``` 或 ```<constructor-arg>``` 元素里, 不需要设置任何 id 或 name 属性

- 内部 Bean 不能使用在任何其他地方

```xml
 <bean id="person2" class="com.pzs.beans.helloworld.Person">
        <property name="name" value="Tom"></property>
        <property name="age" value="24"></property>
        <property name="car">
            <!--  内部bean-->
            <bean class="com.pzs.beans.helloworld.Car">
                <constructor-arg value="Baoma"></constructor-arg>
                <constructor-arg value="Baoma"></constructor-arg>
                <constructor-arg value="300000" type="float"></constructor-arg>
            </bean>
        </property>
    </bean>
```



### 3 null 值和级联属性

•可以使用专用的 ```<null/>```元素标签为 Bean 的字符串或其它对象类型的属性注入 null 值

•和 Struts、Hiberante 等框架一样，**Spring** **支持级联属性的配置**。

```xml
<bean id="person3" class="com.pzs.beans.helloworld.Person">
        <property name="name" value="Tom"></property>
        <property name="age" value="24"></property>
        <property name="car"><null/></property>
    </bean>
```

```xml
<bean id="person4" class="com.pzs.beans.helloworld.Person">
        <constructor-arg value="Jerry"></constructor-arg>
        <constructor-arg value="45"></constructor-arg>
        <constructor-arg ref="car"></constructor-arg>
        <!--级联属性（对象属性）赋值-->
        <!--注意：属性需要先初始化后才可以为级联属性赋值，否则会有异常，和Struct2不同-->
    	<!--car.maxSpeed属性值从200变到250-->
        <property name="car.maxSpeed" value="250"></property>
    </bean>
```

### 4 集合属性

#### list和set

- 在 Spring中可以通过一组内置的 xml 标签(例如: ```<list>```, ```<set>``` 或 ```<map>```) 来配置集合属性.

- 配置 java.util.List 类型的属性, 需要指定``` <list>``` 标签, 在标签里包含一些元素. 这些标签可以通过指定简单的常量值, 通过指定对其他 Bean 的引用. 通过指定内置 Bean 定义. 通过 ```<null/> ```指定空元素. 甚至可以内嵌其他集合.

- 数组的定义和 List 一样, 都使用 ```<list>```

- 配置 java.util.Set 需要使用 ```<set>``` 标签, 定义元素的方法与 List 一样.

```xml
<bean id="personList" class="com.pzs.beans.helloworld.PersonList">
        <constructor-arg value="gully"></constructor-arg>
        <constructor-arg value="18"></constructor-arg>
        <constructor-arg>
            <list>
                <ref bean="car"></ref>
                <ref bean="car2"></ref>
                <ref bean="car3"></ref>
            </list>
        </constructor-arg>
    </bean>
```

#### map

- Java.util.Map 通过```<map>```标签定义, ```<map> ```标签里可以使用多个 ```<entry>```作为子标签. 每个条目包含一个键和一个值.

- 必须在```<key>```标签里定义键

- 因为键和值的类型没有限制, 所以可以自由地为它们指定 ```<value>```,``` <ref>```, ```<bean>```或```<null>```元素.

- 可以将 Map 的键和值作为 ```<entry>``` 的属性定义: 简单常量使用 key 和 value 来定义; Bean 引用通过 key-ref 和 value-ref 属性定义

```xml
<bean id="personMap" class="com.pzs.beans.helloworld.PersonMap">
        <constructor-arg value="jenny"></constructor-arg>
        <constructor-arg value="32"></constructor-arg>
        <constructor-arg>
            <map>
                <entry key="aa" value-ref="car"></entry>
                <entry key="bb" value-ref="car2"></entry>
            </map>
        </constructor-arg>
    </bean>
```

#### properties

- 使用```<props>```定义 java.util.Properties, 该标签使用多个```<prop>```作为子标签. 每个```<prop>```标签必须定义 **key** 属性.

```xml
<bean id="dataSource" class="com.pzs.beans.helloworld.DataSource">
        <property name="properties">
            <props>
                <prop key="user">root</prop>
                <prop key="password">123456</prop>
                <prop key="jdbcUrl">jdbc:mysql://test</prop>
                <prop key="driverClass">com.mysql.jdbc.Driver</prop>
            </props>
        </property>
    </bean>
```

#### 使用util schema定义集合

- 使用基本的集合标签定义集合时, **不能将集合作为独立的** **Bean** 定义,导致其他 **Bean** 无法引用该集合, **所以无法在不同** **Bean** **之间共享集合**.

- 可以使用 util schema 里的集合标签定义独立的集合 Bean. 需要注意的是, 必须在``` <beans> ```根元素里添加 util schema 定义

```xml
xmlns:util="http://www.springframework.org/schema/util"
```

```xml
<!--配置单例的集合bean，以供多个bean进行引用，需要导入util命名空间-->
    <util:list id="cars">
        <ref bean="car"/>
        <ref bean="car2"/>
        <ref bean="car3"/>
    </util:list>

    <bean id="personList2" class="com.pzs.beans.helloworld.PersonList">
        <constructor-arg value="gully"></constructor-arg>
        <constructor-arg value="18"></constructor-arg>
        <constructor-arg ref="cars"></constructor-arg>
    </bean>
```

#### p命名空间简化配置

==xml配置文件导入p命名空间出错，还无法解决==

- 为了简化 XML 文件的配置，越来越多的 XML 文件采用属性而非子元素配置信息。

- Spring 从 2.5 版本开始引入了一个新的 p 命名空间，可以通过 <bean> 元素属性的方式配置 Bean 的属性。

- 使用 p 命名空间后，基于 XML 的配置方式将进一步简化