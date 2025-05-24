package com.scm.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.LoginHelper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        //If user is not authenticated
        if(authentication == null) return;

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(" Adding LoggedIn user information on page");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String email = LoginHelper.getLoggedInUserEmail(authentication);

        logger.info("User logged in: {}", email);

        User user = userService.getUserByEmail(email);
        System.out.println(user.getUsername());

        model.addAttribute("loggedInUser", user);
    }
}
