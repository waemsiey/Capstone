package com.example.capstone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing purposes (remove in production)
                .authorizeRequests(requests -> requests
                        .anyRequest().permitAll())
                //         .requestMatchers("/home").permitAll()
                //         .requestMatchers("/products/**").permitAll()
                //         .requestMatchers("/products/saveProduct").permitAll()
                //         .requestMatchers("/login").permitAll()
                //         .requestMatchers("/admin/**").permitAll() // Allow access to admin products for testing
                //         .requestMatchers("/signup").permitAll()
                //         .requestMatchers("/client/login").permitAll()
                //         .requestMatchers("/client/signup").permitAll()
                //         .requestMatchers("/css/**").permitAll()
                //         .requestMatchers("/js/**").permitAll()
                //         .requestMatchers("/images/**").permitAll()
                //         .anyRequest().authenticated())
                // .formLogin(login -> login
                //         .loginPage("/login")
                //         .permitAll())
                // .logout(logout -> logout
                //         .logoutUrl("/logout")
                //         .logoutSuccessUrl("/login")
                //         .permitAll());
                        .formLogin().disable()
                        .logout().disable();

        return http.build();
    }
}