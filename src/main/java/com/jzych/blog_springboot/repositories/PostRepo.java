package com.jzych.blog_springboot.repositories;

import com.jzych.blog_springboot.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends CrudRepository<Post, Long> {

    List<Post> getByCategory(String category);
    Post getByIdPost(Long id);
    List<Post> getByUserName(String userName);






}
