package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.AuthProviders;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthenticationSuccessHandler");

        // Identify the AuthMethod
        var oAuthAuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClienRegistrationId = oAuthAuthenticationToken.getAuthorizedClientRegistrationId();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(" Authentication Method");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

        logger.info(authorizedClienRegistrationId);

        // Getting user attributes from authentication method
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        logger.info(user.getName());
        user.getAttributes().forEach((key, value) -> {
            logger.info("{} => {}", key, value);
        });

        logger.info(user.getAuthorities().toString());

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setEnabled(true);

        user1.setEmailVerified(true);

        if (authorizedClienRegistrationId.equalsIgnoreCase("google")) {
            // Saving user data to database
            String email = user.getAttribute("email").toString();
            String name = user.getAttribute("name").toString();
            String picture = user.getAttribute("picture").toString();

            // Create user and save it to database
            user1.setEmail(email);
            user1.setUserName(name);
            user1.setProfilePic(picture);
            user1.setPassword("password");
            user1.setAuthMethod(AuthProviders.GOOGLE);
            user1.setAuthProviderId(user1.getUsername());
            user1.setAbout("This account in created with GOOGLE");

            // Save the user in database
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(" User saved in database");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

            User user2 = userRepository.findByEmail(email).orElse(null);
            if (user2 == null) {
                userRepository.save(user1);
                logger.info("User" + email + " Saved in database");
            }

        } else if (authorizedClienRegistrationId.equalsIgnoreCase("github")) {
            String email = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                    : user.getAttribute("login").toString() + "@github.com";
            String picture = user.getAttribute("avatar_url").toString();
            String name = user.getAttribute("name").toString();
            String providerUserId = user.getName();

            user1.setEmail(email);
            user1.setProfilePic(picture);
            user1.setUserName(name);
            user1.setAuthProviderId(providerUserId);
            user1.setAuthMethod(AuthProviders.GITHUB);
            user1.setAbout("This account in created with GITHUB");

            // Save the user in database
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(" User saved in database");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

            User user2 = userRepository.findByEmail(email).orElse(null);
            if (user2 == null) {
                userRepository.save(user1);
                logger.info("User" + email + " Saved in database");
            }
        } else {

        }

        response.sendRedirect("/user/profile");

    }

}
