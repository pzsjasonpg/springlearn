package com.pzs.spring.beans.annonation.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserJdbcRepositoryImpl save ...");
    }
}
