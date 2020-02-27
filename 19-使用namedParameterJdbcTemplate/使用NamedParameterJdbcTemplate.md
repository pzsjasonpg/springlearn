## 在JDBC中使用具名参数

- 在经典的 JDBC 用法中, SQL 参数是用占位符 ? 表示,并且受到位置的限制. 定位参数的问题在于, 一旦参数的顺序发生变化, 就必须改变参数绑定. 
- 在 Spring JDBC 框架中, 绑定 SQL 参数的另一种选择是使用具名参数(named parameter). 
- 具名参数: SQL 按名称(以冒号开头)而不是按位置进行指定. 具名参数更易于维护, 也提升了可读性. 具名参数由框架类在运行时用占位符取代
- 具名参数只在 NamedParameterJdbcTemplate 中得到支持 
- 在 SQL 语句中使用具名参数时, 可以在一个 Map 中提供参数值, 参数名为键
- 也可以使用 SqlParameterSource 参数
- 批量更新时可以提供 Map 或 SqlParameterSource 的数组

```java
/**
     * 使用具名参数---对象
     */
    @Test
    public void testNameParameterJdbcTemplate1() {
        String sql = "INSERT INTO employees(lastname,email,dept_id) VALUES (:lastName,:email,:dept_id)";
        Employee employeeObj = new Employee();
        employeeObj.setLastName("GG");
        employeeObj.setEmail("gg@email.com");
        employeeObj.setDept_id(8);
        SqlParameterSource paramSOurce = new BeanPropertySqlParameterSource(employeeObj);
        namedParameterJdbcTemplate.update(sql,paramSOurce);
    }


    /**
     * 使用具名参数---map映射
     */
    @Test
    public void testNameParameterJdbcTemplate() {
        String sql = "INSERT INTO employees(lastname,email,dept_id) VALUES (:ln,:email,:deptid)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ln","FF");
        paramMap.put("email","ff@email.com");
        paramMap.put("deptid","6");
        namedParameterJdbcTemplate.update(sql,paramMap);
    }
```

