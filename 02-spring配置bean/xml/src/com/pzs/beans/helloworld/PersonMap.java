package com.pzs.beans.helloworld;

import java.util.List;
import java.util.Map;

public class PersonMap {
    private String name;
    private int age;
    private Map<String,Car> car;

    public PersonMap() {
    }

    public PersonMap(String name, int age, Map<String, Car> car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Car> getCar() {
        return car;
    }

    public void setCar(Map<String, Car> car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "PersonMap{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}
