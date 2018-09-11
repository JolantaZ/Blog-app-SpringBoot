package com.jzych.blog_springboot.controllers;

import com.jzych.blog_springboot.models.Post;
import com.jzych.blog_springboot.models.UserEntity;
import com.jzych.blog_springboot.repositories.PostRepo;
import com.jzych.blog_springboot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;


    @Secured("ROLE_ADMIN")
    @GetMapping("/adminarea")
    public String adminPage(){
        return "adminPage";
    }

// do rozwinięcia
    @Secured("ROLE_ADMIN")
    @GetMapping("/deletePost")
    public String deletePost(){
        return "deletePostForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/deletePost")
    public String deletePost(@RequestParam String id_post){ //id_post - taka sama nazwa jak w deletePostForm.html w "name"
        Post postFound = postRepo.getByIdPost(new Long (id_post));
        if(postFound != null){
            postRepo.delete(postFound);
        }
        return "redirect:/adminarea"; //jakiś komunikat że usuniety? albo przekierowaie na posty
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteUser")
    public String deleteUser(){
        return "deleteUserForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String id_user){
        UserEntity userFound = userRepo.getByIdUser(new Long(id_user));
        if(userFound != null){
            userRepo.delete(userFound);
        }
        return "redirect:/adminarea";          //pokazywać komunikat kto jest usunięty?

    }

}
