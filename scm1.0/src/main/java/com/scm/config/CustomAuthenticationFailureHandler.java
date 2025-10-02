package com.scm.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm.helpers.Message;
import com.scm.helpers.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        String errorRedirectUrl = "/login?";
            
        if (exception instanceof BadCredentialsException) {
            // Handle invalid username or password
            System.out.println("Invalid credentials entered");
            errorRedirectUrl += "badcredentials=true";
        }
        else if (exception instanceof DisabledException) {
            // Handle disabled user account
            System.out.println("User is disabled");
            errorRedirectUrl += "disabled=true";
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("message", Message.builder().content("User is disabled").type(MessageType.red). build());
        }
        
        
        response.sendRedirect(errorRedirectUrl);
    }
}