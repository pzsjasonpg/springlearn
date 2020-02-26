## 在 Spring 中启用 AspectJ 注解支持

- 要在 Spring 应用中使用 AspectJ 注解, 必须在 classpath 下包含 AspectJ 类库: aopalliance.jar、aspectj.weaver.jar 和 spring-aspects.jar
- 将 aop Schema 添加到 ```<beans> ```根元素中.
- 要在 Spring IOC 容器中启用 AspectJ 注解支持, 只要在 Bean 配置文件中定义一个空的 XML 元素 ```<aop:aspectj-autoproxy>```
- 当 Spring IOC 容器侦测到 Bean 配置文件中的 ```<aop:aspectj-autoproxy> ```元素时, 会自动为与 AspectJ 切面匹配的 Bean 创建代理.



## 用 AspectJ 注解声明切面

- 要在 Spring 中声明 AspectJ 切面, 只需要在 IOC 容器中将切面声明为 Bean 实例. 当在 Spring IOC 容器中初始化 AspectJ 切面之后, Spring IOC 容器就会为那些与 AspectJ 切面相匹配的 Bean 创建代理.
- 在 AspectJ 注解中, 切面只是一个带有 @Aspect 注解的 Java 类. 
- 通知是标注有某种注解的简单的 Java 方法.
  AspectJ 支持 5 种类型的通知注解: 
  - @Before: 前置通知, 在方法执行之前执行
  - @After: 后置通知, 在方法执行之后执行 
  - @AfterRunning: 返回通知, 在方法返回结果之后执行
  - @AfterThrowing: 异常通知, 在方法抛出异常之后
  - @Around: 环绕通知, 围绕着方法执行



## 利用方法签名编写 AspectJ 切入点表达式

最典型的切入点表达式时根据方法的签名来匹配各种方法:

- execution * com.atguigu.spring.ArithmeticCalculator.*(..): 匹配 ArithmeticCalculator 中声明的所有方法,**第一个 * 代表任意修饰符及任意返回值. 第二个 * 代表任意方法. .. 匹配任意数量的参数**. 若目标类与接口与该切面在同一个包中, 可以省略包名.
- execution public * ArithmeticCalculator.*(..): **匹配 ArithmeticCalculator 接口的所有公有方法**.
- execution public double ArithmeticCalculator.*(..): **匹配 ArithmeticCalculator 中返回 double 类型数值的方法**
- execution public double ArithmeticCalculator.*(double, ..): **匹配第一个参数为 double 类型的方法, .. 匹配任意数量任意类型的参数**
- execution public double ArithmeticCalculator.*(double, double): **匹配参数类型为 double, double 类型的方法.**



### 前置通知

- 前置通知:在方法执行之前执行的通知
- 前置通知使用 @Before 注解, 并将切入点表达式的值作为注解值.

在导入依赖后，在spring xml配置文件中导入context,aop的命名空间。xml配置文件如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <!--    配置自动扫描的包-->
    <context:component-scan base-package="com.pzs.spring.aop.impl"></context:component-scan>
    <!--使AspectJ的通知注解起作用：自动为匹配的类生成代理对象-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

切面java类定义如下：

```java
import java.util.Arrays;
import java.util.List;

//把这个类声明为一个切面：需要将其放置到IOC容器中，再声明为切面
@Aspect
@Component
public class LoggingAspect {

    //声明该方法是个前置通知，在目标方法之前执行
//    @Before("execution(public int com.pzs.spring.aop.impl.CalculatorInterace.add(int,int))")
    @Before("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methosName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method " + methosName + " begins with " + args);
    }
}
```



### 后置通知

- 后置通知是在连接点完成之后执行的, 即连接点返回结果或者抛出异常的时候, 下面的后置通知记录了方法的终止. 
- 一个切面可以包括一个或者多个通知.

```java
//后置通知：在目标方法执行后（无论是否发生异常都执行）
    //注意：后置通知不能访问目标方法返回的结果，需要在返回通知才能访问
    @After("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        String methosName = joinPoint.getSignature().getName();
        System.out.println("The method " + methosName + " ends");
    }
```



### 返回通知

无论连接点是正常返回还是抛出异常, 后置通知都会执行. 如果只想在连接点返回的时候记录日志, 应使用返回通知代替后置通知.

- 在返回通知中, 只要将 **returning** 属性添加到 @AfterReturning 注解中, 就可以访问连接点的返回值. 该属性的值即为用来传入返回值的参数名称. 
- 必须在通知方法的签名中添加一个**同名参数**. 在运行时, Spring AOP 会通过这个参数传递返回值.
- **原始的切点表达式需要出现在 pointcut 属性中**

```java
/**
     * 方法正常结束受执行的代码
     * 返回通知是可以访问到方法的返回值
     */
    @AfterReturning(value = "execution(* com.pzs.spring.aop.impl.*.*(..))",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知：The method " + methodName + " ends with " + result);
    }
```



### 异常通知

- 只在连接点抛出异常时才执行异常通知
- **将 throwing 属性添加到 @AfterThrowing 注解中,** 也可以访问连接点抛出的异常. Throwable 是所有错误和异常类的超类. 所以在异常通知方法可以捕获到任何错误和异常.
- **如果只对某种特殊的异常类型感兴趣**, 可以将参数声明为其他异常的参数类型. 然后通知就只在抛出这个类型及其子类的异常时才被执行.

```java
/**
     * 目标方法出现异常时执行代码
     * 返回通知可以访问到异常对象，且可以指定在出现特定异常时执行通知代码
     */
    @AfterThrowing(value = "execution(* com.pzs.spring.aop.impl.*.*(..))", throwing = "ex")
    public void affterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知:he method " + methodName + " occurs exception: " + ex);
    }
```



### 环绕通知

- 环绕通知是所有通知类型中功能最为强大的, 能够全面地控制连接点. 甚至可**以控制是否执行连接点**.
- **对于环绕通知来说, 连接点的参数类型必须是 ProceedingJoinPoint . 它是 JoinPoint 的子接口, 允许控制何时执行, 是否执行连接点.**
- **在环绕通知中需要明确调用 ProceedingJoinPoint 的 proceed() 方法来执行被代理的方法. 如果忘记这样做就会导致通知被执行了, 但目标方法没有被执行.**
- **注意: 环绕通知的方法需要返回目标方法执行之后的结果, 即调用 joinPoint.proceed(); 的返回值, 否则会出现空指针异常**

```java
/**
     * 环绕通知需要携带ProceedingJoinPoint类型参数。
     * 环绕通知类似于动态代理全过程，ProceedingJoinPoint类型参数可以决定是否执行目标方法。
     * 且环绕方法必须有返回值。
     */
    @Around("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        try {
            System.out.println("环绕通知:The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            result = pjd.proceed();
            System.out.println("环绕通知：The method " + methodName + " ends with " + result);
        } catch (Throwable throwable) {
            System.out.println("环绕通知:he method " + methodName + " occurs exception: " + throwable);
            throw new RuntimeException(throwable);
        }
        System.out.println("环绕通知：The method " + methodName + " ends");
        return result;
    }
```

