package com.jzych.blog_springboot.service;

import com.jzych.blog_springboot.models.UserDto;
import com.jzych.blog_springboot.models.UserEntity;

public interface IUserService {
    UserEntity registerNewUserAccount(UserDto accountDto)
        throws LoginExistsException;
}
