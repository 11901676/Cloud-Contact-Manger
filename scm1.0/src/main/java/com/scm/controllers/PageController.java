package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page accessed");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        System.out.println("About page accessed");
        return "about";
    }

    @RequestMapping("/services")
    public String services(Model model) {
        System.out.println("Services page accessed");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact(Model model) {
        System.out.println("Contact page accessed");
        return "contact";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpSession httpSession) {
        System.out.println("Login page accessed");
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        System.out.println("Register page accessed");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // Processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession httpSession) {
        System.out.println("++++++++++++++++++++++++++");
        System.out.println(" Processing registration");
        System.out.println("++++++++++++++++++++++++++");

        System.out.println(userForm);

        // Validating user data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        // Saving user data from from to database
        User user = new User();
        user.setUserName(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("https://www.flaticon.com/free-icon/boy_16683419");

        User savedUser = userService.saveUser(user);
        System.out.println("User Saved");

        // Giving confirmation message to user on page
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        httpSession.setAttribute("message", message);

        return "redirect:/register";
    }
}
