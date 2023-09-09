package com.vathana.ecommercespring.service;

import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfilesByJwt(String jwt) throws UserException;

}
