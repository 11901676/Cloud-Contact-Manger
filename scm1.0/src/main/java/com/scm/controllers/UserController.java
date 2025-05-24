package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // user dashboard
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "/user/dashboard";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "/user/profile";
    }
}
