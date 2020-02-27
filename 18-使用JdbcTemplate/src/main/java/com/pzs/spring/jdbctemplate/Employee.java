package com.pzs.spring.jdbctemplate;

public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer dept_id;

    public Employee() {
    }

    public Employee(Integer id, String lastName, String email, Integer dept_id) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.dept_id = dept_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dept_id=" + dept_id +
                '}';
    }
}
