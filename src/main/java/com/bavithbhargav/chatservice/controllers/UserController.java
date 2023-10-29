package com.bavithbhargav.chatservice.controllers;

import com.bavithbhargav.chatservice.collections.User;
import com.bavithbhargav.chatservice.models.ChatServiceResponseMessage;
import com.bavithbhargav.chatservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user-service", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/test")
    public ResponseEntity<ChatServiceResponseMessage> testEndpoint() {
        return new ResponseEntity<>(new ChatServiceResponseMessage("Server is up and running"), HttpStatus.OK);
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("api/v1/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);
    }

    @GetMapping("api/v1/users")
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "5") int pageSize) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }


}
