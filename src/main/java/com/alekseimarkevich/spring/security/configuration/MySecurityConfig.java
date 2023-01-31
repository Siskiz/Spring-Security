package com.alekseimarkevich.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class MySecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public UserDetailsService users() {
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//        UserDetails user1 = userBuilder
//                .username("aleksei")
//                .password("aleksei")
//                .roles("EMPLOYEE")
//                .build();
//        UserDetails user2 = userBuilder
//                .username("elena")
//                .password("elena")
//                .roles("HR")
//                .build();
//        UserDetails user3 = userBuilder
//                .username("oleg")
//                .password("oleg")
//                .roles("MANAGER", "HR")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2, user3);
    return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
                .requestMatchers("/hr_info").hasRole("HR")
                .requestMatchers("/manager_info").hasRole("MANAGER")
                .and().formLogin().permitAll();
        return http.build();
    }

}
