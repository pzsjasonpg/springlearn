package com.pzs.spring.beans.annonation.repository;

import com.pzs.spring.beans.annonation.TestObject;
import com.sun.corba.se.impl.orb.ParserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired(required = false)
    TestObject testObject;

    public void save() {
        System.out.println("UserRepositoryImpl save ...");
        System.out.println(testObject);
    }
}
