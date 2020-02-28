事务管理是企业级应用程序开发中必不可少的技术,  用来确保数据的完整性和一致性. 
事务就是一系列的动作, 它们被当做一个单独的工作单元. 这些**动作要么全部完成, 要么全部不起作用**
事务的四个关键属性(ACID)

- 原子性(atomicity): 事务是一个原子操作, 由一系列动作组成. 事务的原子性确保动作要么全部完成要么完全不起作用.
- 一致性(consistency): 一旦所有事务动作完成, 事务就被提交. 数据和资源就处于一种满足业务规则的一致性状态中.
- 隔离性(isolation): 可能有许多事务会同时处理相同的数据, 因此每个事物都应该与其他事务隔离开来, 防止数据损坏.
- 持久性(durability): 一旦事务完成, 无论发生什么系统错误, 它的结果都不应该受到影响. 通常情况下, 事务的结果被写到持久化存储器中.

## Spring 中的事务管理

作为企业级应用程序框架, Spring 在不同的事务管理 API 之上定义了一个抽象层. 而应用程序开发人员不必了解底层的事务管理 API, 就可以使用 Spring 的事务管理机制.
Spring 既支持编程式事务管理, 也支持声明式的事务管理. 
编程式事务管理: 将事务管理代码嵌入到业务方法中来控制事务的提交和回滚. 在编程式管理事务时, 必须在每个事务操作中包含额外的事务管理代码. 
**声明式事务管理**: 大多数情况下比编程式事务管理更好用. 它将事务管理代码从业务方法中分离出来, 以声明的方式来实现事务管理. 事务管理作为一种横切关注点, 可以通过 AOP 方法模块化. Spring 通过 Spring AOP 框架支持声明式事务管理.



## Spring 中的事务管理器

Spring 从不同的事务管理 API 中抽象了一整套的事务机制. 开发人员不必了解底层的事务 API, 就可以利用这些事务机制. 有了这些事务机制, 事务管理代码就能独立于特定的事务技术了.
Spring 的核心事务管理抽象是 **PlatformTransactionManager**，它为事务管理封装了一组独立于技术的方法. 无论使用 Spring 的哪种事务管理策略(编程式或声明式), 事务管理器都是必须的.



## Spring 中的事务管理器的不同实现

**DataSourceTransactionManager**: 在应用程序中只需要处理一个数据源, 而且通过 JDBC 存取

**JtaTransactionManager**: 在 JavaEE 应用服务器上用 JTA(Java Transaction API) 进行事务管理

**HibernateTransactionManager**: 用 Hibernate 框架存取数据库



##  用 @Transactional 注解声明式地管理事务

除了在带有切入点, 通知和增强器的 Bean 配置文件中声明事务外, Spring 还允许简单地用 @Transactional 注解来标注事务方法. 
为了将方法定义为支持事务处理的, 可以为方法添加 @Transactional 注解. 根据 Spring AOP 基于代理机制, 只能标注公有方法.
可以在方法或者类级别上添加 @Transactional 注解. 当把这个注解应用到类上时, 这个类中的所有公共方法都会被定义成支持事务处理的. 
在 Bean 配置文件中只需要启用 ```<tx:annotation-driven> ```元素, 并为之指定事务管理器就可以了. 
如果事务处理器的名称是 transactionManager, 就可以在```<tx:annotation-driven>``` 元素中省略 transaction-manager 属性. 这个元素会自动检测该名称的事务处理器.

```xml
	<context:component-scan base-package="com.atguigu.spring"></context:component-scan>
	
	<!-- 导入资源文件 -->
	<context:property-placeholder location="classpath:db.properties"/>
	
	<!-- 配置 C3P0 数据源 -->
	<bean id="dataSource"
		class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="driverClass" value="${jdbc.driverClass}"></property>

		<property name="initialPoolSize" value="${jdbc.initPoolSize}"></property>
		<property name="maxPoolSize" value="${jdbc.maxPoolSize}"></property>
	</bean>
	<!-- 配置事务管理器 -->
	<bean id="transactionManager" 
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	
	<!-- 启用事务注解 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>
```



## 事务传播属性

- 当事务方法被另一个事务方法调用时, 必须指定事务应该如何传播. 例如: 方法可能继续在现有事务中运行, 也可能开启一个新事务, 并在自己的事务中运行.
- 事务的传播行为可以由传播属性指定. Spring 定义了 7  种类传播行为.

| 传播属性      | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| REQURED       | 如果有事务在运行，当前的方法就在这个食物内运行，否则，就启动一个新的事务，并在自己的事务内运行。 |
| REQURED_NEW   | 当前的方法必须启动新事务，并在它自己的事务内运行，如果有事务正在运行，应该将它挂起。 |
| SUPPORTS      | 如果有事务在运行，当前的方法就在这个事务内运行，否则它可以不运行在事务中。 |
| NOT_SUPPORTED | 当前的方法不应该运行在事务内部，如果有运行的事务，将它挂起。 |
| MANDATORY     | 当前的方法必须运行在事务中，如果没有正在运行的事务，就抛出异常 |
| NERVER        | 当前的方法不应该运行在事务中，如果有运行的事务，就抛出异常   |
| NESTED        | 如果有事务在运行，当前的方法就应该在这个事务的嵌套事务内运行，否则，就启动一个新的事务，并在它自己的事务内运行。 |



## 并发事务所导致的问题

当同一个应用程序或者不同应用程序中的多个事务在同一个数据集上并发执行时, 可能会出现许多意外的问题
并发事务所导致的问题可以分为下面三种类型:

- **脏读**: 对于两个事物 T1, T2, T1  读取了已经被 T2 更新但 还没有被提交的字段. 之后, 若 T2 回滚, T1读取的内容就是临时且无效的.
- **不可重复读**:对于两个事物 T1, T2, T1  读取了一个字段, 然后 T2 更新了该字段. 之后, T1再次读取同一个字段, 值就不同了.
- **幻读**:对于两个事物 T1, T2, T1  从一个表中读取了一个字段, 然后 T2 在该表中插入了一些新的行. 之后, 如果 T1 再次读取同一个表, 就会多出几行.

### 事务的隔离级别

从理论上来说, 事务应该彼此完全隔离, 以避免并发事务所导致的问题. 然而, 那样会对性能产生极大的影响, 因为事务必须按顺序运行. 
在实际开发中, 为了提升性能, 事务会以较低的隔离级别运行.
事务的隔离级别可以通过隔离事务属性指定

| 隔离级别        | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| DEFAULT         | 使用底层数据库的默认隔离级别，对于大多数数据库来说，默认隔离级别都是READ_COMMITED |
| READ_UNCOMMITED | 允许事务读取被其他事务提交的变更，脏读，不可重复读和幻读的问题都会出现。 |
| READ_COMMITED   | 只运行事务读取已经被其他事务提交的变更，可以避免脏读，但不可重复读和幻读的问题仍然会出现 |
| REPEATABLE_READ | 确保事务可以多次从一个字段中读取相同的值，在这个事务持续期间，禁止其他事务对这个字段进行更新，可以避免脏读和重复读，但换读的问题仍然会出现。 |
| SERIALIZABLE    | 确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表执行插入，更新和删除操作，所有并发问题都可以避免，但是性能十分低下。 |



事务的隔离级别要得到底层数据库引擎的支持, 而不是应用程序或者框架的支持.
Oracle 支持的 2 种事务隔离级别：READ_COMMITED , SERIALIZABLE
Mysql 支持 4 中事务隔离级别.

### 设置隔离事务属性

- 用 @Transactional 注解声明式地管理事务时可以在 @Transactional 的 isolation 属性中设置隔离级别.
- 在 Spring 2.x 事务通知中, 可以在 ```<tx:method>``` 元素中指定隔离级别

### 设置回滚事务属性

**默认情况下只有未检查异常**(RuntimeException和Error类型的异常)**会导致事务回滚**. 而受检查异常不会.
事务的回滚规则可以通过 @Transactional 注解的 **rollbackFor** 和 **noRollbackFor** 属性来定义. 这两个属性被声明为 Class[] 类型的, 因此可以为这两个属性指定多个异常类.

- rollbackFor:  遇到时必须进行回滚
- noRollbackFor: 一组异常类，遇到时必须不回滚

在 Spring 2.x 事务通知中, 可以在 ```<tx:method>``` 元素中指定回滚规则. 如果有不止一种异常, 用逗号分隔.

### 超时和只读属性

由于事务可以在行和表上获得锁,  因此长事务会占用资源, 并对整体性能产生影响. 
如果一个事物只读取数据但不做修改, 数据库引擎可以对这个事务进行优化.
**超时事务属性**: 事务在强制回滚之前可以保持多久. 这样可以防止长期运行的事务占用资源.
**只读事务属性**: 表示这个事务只读取数据但不更新数据, 这样可以帮助数据库引擎优化事务.

- 超时和只读属性可以在 @Transactional 注解中定义.超时属性以秒为单位来计算.（**readOnly**和**timeout**属性）
- 在 Spring 2.x 事务通知中, 超时和只读属性可以在 ```<tx:method> ```元素中进行指定.