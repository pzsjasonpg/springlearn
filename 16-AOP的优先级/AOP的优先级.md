当有多种切面，比如校验、日志等切面时，优先级是不确定的。

所以需要指定优先级。



- 在同一个连接点上应用不止一个切面时, 除非明确指定, 否则它们的优先级是不确定的.
- 切面的优先级可以通过实现 Ordered 接口或利用 @Order 注解指定.
- 实现 Ordered 接口, getOrder() 方法的返回值越小, 优先级越高.
- 若使用 @Order 注解, 序号出现在注解中



```java
@Order(1)
@Aspect
@Component
public class ValidateAspect {

    @Before("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void validateArgs(JoinPoint joinPoint) {
        System.out.println(">> validate: " + Arrays.asList(joinPoint.getArgs()));
    }
}
```

```java
@Order(2)
@Aspect
@Component
public class LoggingAspect {

    //声明该方法是个前置通知，在目标方法之前执行
//    @Before("execution(public int com.pzs.spring.aop.impl.CalculatorInterace.add(int,int))")
    @Before("execution(* com.pzs.spring.aop.impl.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("前置通知:The method " + methodName + " begins with " + args);
    }
}
```

