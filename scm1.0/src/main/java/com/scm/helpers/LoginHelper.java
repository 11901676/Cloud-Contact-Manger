package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class LoginHelper {
    public static String getLoggedInUserEmail(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println(" User Logged in with GOOGLE");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                return user.getAttribute("email").toString();
            } else if (clientId.equalsIgnoreCase("github")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println(" User Logged in with GITHUB");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                String email = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                        : user.getAttribute("login").toString() + "@github.com";
                System.out.println("User email is: " + email);
                System.out.println("User name is: " + user.getAttribute("name").toString());

                return email;
            }
        } else {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(" User Logged in form LOCAL DATABASE");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
            return authentication.getName();
        }
        return "User Email Can't be found";
    }
}
