

为了使 JDBC 更加易于使用, Spring 在 JDBC API 上定义了一个抽象层, 以此建立一个 JDBC 存取框架.
作为 Spring JDBC 框架的核心, JDBC 模板的设计目的是为不同类型的 JDBC 操作提供模板方法. 每个模板方法都能控制整个过程, 并允许覆盖过程中的特定任务. 通过这种方式, 可以在尽可能保留灵活性的情况下, 将数据库存取的工作量降到最低.



```java
/**
     * 获取单个列的值
     */
    @Test
    public void testQueryForObject2() {
        String sql = "SELECT count(id) FROM employees";
        long count = jdbcTemplate.queryForObject(sql,Long.class);
        System.out.println(count);

    }

    /**
     * 查询实体类的集合
     */
    @Test
    public void testQueryForList() {
        String sql = "SELECT id, lastname, email, dept_id FROM employees WHERE id>?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employees = jdbcTemplate.query(sql,rowMapper,1);
        System.out.println(employees);
    }

    /**
     * 根据条件查询数据，返回对象
     * 不支持级联属性
     */
    @Test
    public void testQueryForObject() {
        String sql = "SELECT id, lastname, email, dept_id FROM employees WHERE id=?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql,rowMapper,1);
        System.out.println(employee);
    }


    /**
     * 批量插入信息
     */
    @Test
    public void testBatchUpdate() {
        String sql = "INSERT INTO employees(lastname,email,dept_id) VALUES(?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"AA","aa@email.com",4});
        batchArgs.add(new Object[]{"BB","bb@email.com",5});
        batchArgs.add(new Object[]{"CC","cc@email.com",6});
        jdbcTemplate.batchUpdate(sql,batchArgs);
    }

    /**
     * 更新数据
     */
    @Test
    public void update() {
        String sql = "UPDATE employees SET lastname=? WHERE id=?";
        jdbcTemplate.update(sql,"pzs",1);
    }

    @Test
    public void insert() {
        String sql = "INSERT INTO employees (lastname,email,dept_id) VALUES ('jasonpg','pengzhangsheng@163.com',2)";
        jdbcTemplate.execute(sql);
    }
```

