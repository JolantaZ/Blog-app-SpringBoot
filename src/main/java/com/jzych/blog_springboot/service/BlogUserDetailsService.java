package com.jzych.blog_springboot.service;

import com.jzych.blog_springboot.models.UserEntity;
import com.jzych.blog_springboot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;


@Service
public class BlogUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;


    @Override //załdowane automatycznie po implementacji UserDetailsService
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {// dane są
        // sprawdzane z formularzem logowania podczas logowania

        UserEntity activeUserinfo = userRepo.getByLogin(login);
        if(activeUserinfo == null){
            throw new UsernameNotFoundException("User not found");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(activeUserinfo.getRole());

        String userLogin = activeUserinfo.getLogin();
        String userPassword = activeUserinfo.getPassword();

        UserDetails userDetails = new User(userLogin, userPassword, Arrays.asList(authority));
        return userDetails;

    }



}
