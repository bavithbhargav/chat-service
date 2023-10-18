package com.bavithbhargav.chatservice.controllers;

import com.bavithbhargav.chatservice.collections.User;
import com.bavithbhargav.chatservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(value = "/user-service", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws HttpClientErrorException {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("api/v1/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);
    }


}
