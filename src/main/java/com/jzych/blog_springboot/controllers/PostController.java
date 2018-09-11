package com.jzych.blog_springboot.controllers;

import com.jzych.blog_springboot.models.Post;
import com.jzych.blog_springboot.repositories.PostRepo;
import com.jzych.blog_springboot.repositories.UserRepo;
import com.jzych.blog_springboot.service.BlogUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    UserRepo userRepo;


    @Autowired
    BlogUserDetailsService userDetailsService;



    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/addPost")
    public String addPost(Model model){
        model.addAttribute("post", new Post());
        return "postFormView";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/addPost")
    public String addPost(@ModelAttribute Post post){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();//pobiera login

//        System.out.println(principal.getName());// login pobiera
//        System.out.println(userDetailsService.loadUserByUsername(currentName));//dane z interfejsu UserDetails

        String postUserName = userRepo.getNameByLogin(currentName);
        Long postIdUser = userRepo.getIdByLogin(currentName);
        post.setUserName(postUserName);
        post.setUserId(postIdUser);

        postRepo.save(post);

        return "redirect:/showPosts";
    }


    @GetMapping("/showPosts")
    public String showPosts(Model model){
        Iterable<Post> allPosts = postRepo.findAll();
        model.addAttribute("allPosts", allPosts);
        return "showPostsPage";
    }

    @GetMapping("/showPostsCat")
    public String findByCategory(){
        return "showPostsPage";
    }


    @PostMapping("/showPostsCat")
    public String findByCategory(@RequestParam String category, Model model) {
        Iterable<Post> postsFoundedByCat = postRepo.getByCategory(category);
        model.addAttribute("foundedByCategory", postsFoundedByCat);
        return "showPostsPage";
    }

    @GetMapping("/showPostsByAuthor")
    public String findByAuthor(){
        return "showPostsPage";
    }

    @PostMapping("/showPostsByAuthor")
    public String findByAuthor(@RequestParam String author_name, Model model){
        Iterable<Post> postsFoundedByAuth = postRepo.getByUserName(author_name);
        model.addAttribute("foundedByAuthor", postsFoundedByAuth);
        return "showPostsPage";
    }
}
