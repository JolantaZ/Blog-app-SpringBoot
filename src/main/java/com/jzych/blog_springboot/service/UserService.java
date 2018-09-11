package com.jzych.blog_springboot.service;

import com.jzych.blog_springboot.models.UserDto;
import com.jzych.blog_springboot.models.UserEntity;
import com.jzych.blog_springboot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;



    @Override
    public UserEntity registerNewUserAccount(UserDto accountDto) throws LoginExistsException
    {
        if(loginExist(accountDto.getLogin())){
            throw new LoginExistsException(
                    "There is an account with that login:" + accountDto.getLogin()); //nie pojawia się
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName(accountDto.getName());
        userEntity.setLogin(accountDto.getLogin());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        userEntity.setRole(accountDto.getRole());

        return userRepo.save(userEntity);

    }

    private boolean loginExist(String login){
        UserEntity userEntity = userRepo.getByLogin(login);
                if(userEntity != null) {
                    return true;
                }
                return false;
    }
}
