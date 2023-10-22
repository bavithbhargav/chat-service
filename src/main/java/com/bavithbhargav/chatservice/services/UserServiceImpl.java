package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.User;
import com.bavithbhargav.chatservice.repositories.UserRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB != null) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "User with email already exists");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setUserId(MongoDBUtils.generateRandomPrimaryID(10));
        user.setPassword(encodedPassword);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(user -> {
            user.setPassword(null);
            return user;
        });
    }

    @Override
    public User loginUser(User user) {
        if (user.getEmail() == null || user.getPassword() == null ||
                user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Email or password is empty");
        }
        User userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with email not found");
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userFromDB.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        userFromDB.setPassword(null);
        return userFromDB;
    }

}
