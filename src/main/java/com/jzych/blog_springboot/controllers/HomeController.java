package com.jzych.blog_springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(){
        return "homePage";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "aboutPage";
    }

    @GetMapping("/error-view")
    public String errorHandler(){
        return "errorPage";
    }








}
