package com.pzs.spring.beans.annonation;

import com.pzs.spring.beans.annonation.controller.UserController;
import com.pzs.spring.beans.annonation.repository.UserRepository;
import com.pzs.spring.beans.annonation.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annonation.xml");
//        TestObject testObject = (TestObject) ctx.getBean("testObject");
//        System.out.println(testObject);

        UserController userController = (UserController) ctx.getBean("userController");
        userController.execute();

//        UserService userService = (UserService) ctx.getBean("userService");
//        System.out.println(userService);
//
//        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
//        System.out.println(userRepository);
    }

}
