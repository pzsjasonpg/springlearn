package com.pzs.spring.jdbctemplate;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTest {
    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate = null;
    private EmployeeDao employee = null;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;

    {
        ctx = new ClassPathXmlApplicationContext("jdbcTemplate.xml");
        jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        employee = ctx.getBean(EmployeeDao.class);
        namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) ctx.getBean("nameParameterJdbcTemplate");
    }

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



    @Test
    public void testEmployeeDao() {
        Employee employee = this.employee.getEmployeeById(1);
        System.out.println(employee);
    }

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

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        System.out.println(dataSource.getConnection());
    }

}
