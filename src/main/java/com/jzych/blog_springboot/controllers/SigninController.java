package com.jzych.blog_springboot.controllers;

import com.jzych.blog_springboot.models.UserDto;
import com.jzych.blog_springboot.models.UserEntity;
import com.jzych.blog_springboot.repositories.UserRepo;
import com.jzych.blog_springboot.service.LoginExistsException;
import com.jzych.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class SigninController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService service;


    @GetMapping("/register")
    public String registerForm(Model model, WebRequest request){
        UserDto accountDto = new UserDto();
        model.addAttribute("userentity", accountDto);
        return "signinPage";
    }


    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("userentity") @Valid UserDto accountDto,
                                            BindingResult result, WebRequest request, Errors errors){

        UserEntity registered = new UserEntity();
        if(!result.hasErrors()){
            registered = createUserAccount(accountDto, result);
        }
        if(registered == null){
            result.rejectValue("login", "message.regError");
        }
        if(result.hasErrors()){
            return  new ModelAndView("signinError", "userentity", accountDto);
        }
        else{
            return new ModelAndView("homePage", "userentity", accountDto);
        }

    }

    private UserEntity createUserAccount(@Valid UserDto accountDto, BindingResult result) {
        UserEntity registered = null;
        try{
            registered = service.registerNewUserAccount(accountDto);
        }catch (LoginExistsException e) {
            return null;
        }
        return registered;
    }


}
