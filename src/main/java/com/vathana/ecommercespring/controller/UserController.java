package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

}
