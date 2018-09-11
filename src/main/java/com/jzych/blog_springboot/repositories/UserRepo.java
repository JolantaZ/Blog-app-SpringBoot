package com.jzych.blog_springboot.repositories;

import com.jzych.blog_springboot.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository <UserEntity, Long> {

    UserEntity getByName(String name);
    UserEntity getByIdUser(Long id);
    UserEntity getByLogin(String login);

    @Query("SELECT u.name FROM UserEntity u WHERE u.login=?1")
    String getNameByLogin(String login);

    @Query("SELECT u.idUser FROM UserEntity u WHERE u.login=?1")
    Long getIdByLogin(String login);


}
