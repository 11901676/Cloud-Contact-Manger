package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.services.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
   @Autowired
   private SecurityCustomUserDetailService userDetailService;

   @Autowired
   private OAuthAuthenticationSuccessHandler successHandler;

   @Bean
   public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

      daoAuthenticationProvider.setUserDetailsService(userDetailService);
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

      return daoAuthenticationProvider;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
      httpSecurity.authorizeHttpRequests(authorize -> {
         authorize.requestMatchers("/user/**").authenticated();
         authorize.anyRequest().permitAll();
      });

      // setting our login page
      httpSecurity.formLogin(formLogin -> {
         formLogin.loginPage("/login");
         formLogin.loginProcessingUrl("/authenticate");
         formLogin.successForwardUrl("/user/profile");
         // formLogin.failureForwardUrl("/login?erro=true");
         formLogin.usernameParameter("email");
         formLogin.passwordParameter("password");

         // formLogin.failureHandler(new AuthenticationFailureHandler() {

         // @Override
         // public void onAuthenticationFailure(HttpServletRequest request,
         // HttpServletResponse response,
         // AuthenticationException exception) throws IOException, ServletException {
         // // TODO Auto-generated method stub
         // throw new UnsupportedOperationException("Unimplemented method
         // 'onAuthenticationFailure'");
         // }

         // });
      });

      // Disabling csrf
      httpSecurity.csrf(AbstractHttpConfigurer::disable);

      // Logging out of the form
      httpSecurity.logout(logoutForm -> {
         logoutForm.logoutUrl("/logout");
         System.out.println("~~~~~~~~~~~~~~~~~~~");
         System.out.println(" User Logged Out");
         System.out.println("~~~~~~~~~~~~~~~~~~~");
         logoutForm.logoutSuccessUrl("/login?logout=true");
      });

      // oAuth Configuration
      httpSecurity.oauth2Login(oauth -> {
         oauth.loginPage("/login");
         oauth.successHandler(successHandler);
      });

      return httpSecurity.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

}
