package com.jzych.blog_springboot.service;


@SuppressWarnings("serial")
public class LoginExistsException extends Throwable {

    public LoginExistsException(final String message){
        super(message);
    }
}
