package com.pzs.spring.beans.annonation.service;

import com.pzs.spring.beans.annonation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
