```<context:component-scan> ```元素还会自动注册 AutowiredAnnotationBeanPostProcessor 实例, 该实例可以自动装配具有 **@Autowired** 和 @Resource 、@Inject注解的属性.

## @Autowired

@Autowired 注解自动装配具有兼容类型的单个 Bean属性

- 构造器, 普通字段(即使是非 public), 一切具有参数的方法都可以应用@Authwired 注解
- 默认情况下, 所有使用 @Authwired 注解的属性都需要被设置. 当 Spring 找不到匹配的 Bean 装配属性时, 会抛出异常, 若某一属性允许不被设置, 可以设置 @Authwired 注解的 required 属性为 false
- 默认情况下, 当 IOC 容器里存在多个类型兼容的 Bean 时, 通过类型的自动装配将无法工作. 此时可以在 @Qualifier 注解里提供 Bean 的名称. Spring 允许对方法的入参标注 @Qualifiter 已指定注入 Bean 的名称
- @Authwired 注解也可以应用在数组类型的属性上, 此时 Spring 将会把所有匹配的 Bean 进行自动装配.
- @Authwired 注解也可以应用在集合属性上, 此时 Spring 读取该集合的类型信息, 然后自动装配所有与之兼容的 Bean. 
- @Authwired 注解用在 java.util.Map 上时, 若该 Map 的键值为 String, 那么 Spring 将自动装配与之 Map 值类型兼容的 Bean, 此时 Bean 的名称作为键值



### 当 Spring 找不到匹配的 Bean 装配属性

将TestObject类的注解**@Compoent**去掉。

```java
public class TestObject {
}
```

在UserRepositoryImpl类中添加装配TestObject。

```java
@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    TestObject testObject;

    public void save() {
        System.out.println("UserRepositoryImpl save ...");
        System.out.println(testObject);
    }
}
```

此时找不到装配的bean，testObject。**此时会报异常**。



若某一属性允许不被设置, 可以设置 @Authwired 注解的 required 属性为 false。

```java
@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository {

    @Autowired(required = false)
    TestObject testObject;

    public void save() {
        System.out.println("UserRepositoryImpl save ...");
        System.out.println(testObject);
    }
}
```

此时输出为null：

```UserController execute ...
UserController execute ...
UserService add ...
UserRepositoryImpl save ...
null
```



### 容器里存在多个类型兼容的 Bean

#### @Repository注解指定匹配名

创建一个类UserJdbcRepositoryImpl实现UserRepository接口。

```java
@Repository
public class UserJdbcRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserJdbcRepositoryImpl save ...");
    }
}
```

此时有两个类实现了UserRepository接口，**UserJdbcRepositoryImpl**和**UserRepositoryImpl**。

在UserService里面装配时,使用的时userRepository名装配。

```java
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void add() {
        System.out.println("UserService add ...");
        userRepository.save();
    }
}
```

如果UserRepositoryImpl的注解Repository的value值为userRepository，则不会报异常。因为装配时遇到有多个类型兼容的bean时，会去找名字相同的那个。所以装配的bean是UserRepositoryImpl类的bean。

```java
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    @Autowired(required = false)
    TestObject testObject;

    public void save() {
        System.out.println("UserRepositoryImpl save ...");
        System.out.println(testObject);
    }
}
```

#### @Qualifier注解指定装配bean

如果@Repository注解没有指定匹配的名字。则在UserService类中装配bean是用Qualifier注解指定装配bean。

```java
@Service
public class UserService {

    @Autowired
    @Qualifier("userRepositoryImpl")
    UserRepository userRepository;

    public void add() {
        System.out.println("UserService add ...");
        userRepository.save();
    }
}
```

此时同样没有问题。

==注意，@Repository注解指定匹配名和@Qualifier注解指定装配bean不能同时使用==



- Spring 还支持 @Resource 和 @Inject 注解，这两个注解和 @Autowired 注解的功用类似
- @Resource 注解要求提供一个 Bean 名称的属性，若该属性为空，则自动采用标注处的变量或方法名作为 Bean 的名称
- @Inject 和 @Autowired 注解一样也是按类型匹配注入的 Bean， 但没有 reqired 属性
  建议使用 @Autowired 注解