package com.example.security.config;

import com.example.security.constant.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.security.constant.Role.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // In-memory authentication
    // 1. Create in-memory user bean
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userStudent = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user@123"))
                .roles(STUDENT.name())
                .build();
        UserDetails userAdmin = User.builder().username("admin")
                .password(passwordEncoder.encode("admin@123"))
                .roles(ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(userStudent, userAdmin);
    }

    // 2. Instead of create an in-memory user bean, we can create authenticated information here
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password(passwordEncoder.encode("secret"))
//                .roles("USER");
//    }

}
