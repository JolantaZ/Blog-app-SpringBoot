package com.jzych.blog_springboot.configurations;

import com.jzych.blog_springboot.service.BlogUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // umożliwia  dodawanie @Secured({ "ROLE_USER" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BlogUserDetailsService blogUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home").hasAnyRole("ADMIN","USER", "ANONYMOUS")
                .and()
                .formLogin()         //podstawia formularz domyślny
                .loginPage("/login")       //Specifies the URL to send users to if login is required
                .loginProcessingUrl("/login")     //Specifies the URL to validate the credentials
                .usernameParameter("sec_login")
                .passwordParameter("sec_password")
                .defaultSuccessUrl("/home")
                //.failureUrl("/error")
                .and()// . działa jak znak zamknięcia np. />

                .logout()   // https://docs.spring.io/spring-security/site/docs/5.0.5.RELEASE/reference/htmlsingle/#jc-logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()

                .exceptionHandling().accessDeniedPage("/error-view");

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authenticationManagerBuilder.userDetailsService(blogUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
