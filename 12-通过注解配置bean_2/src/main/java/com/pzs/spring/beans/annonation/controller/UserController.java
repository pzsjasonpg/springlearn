package com.pzs.spring.beans.annonation.controller;

import com.pzs.spring.beans.annonation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    public void execute() {
        System.out.println("UserController execute ...");
        userService.add();
    }
}
