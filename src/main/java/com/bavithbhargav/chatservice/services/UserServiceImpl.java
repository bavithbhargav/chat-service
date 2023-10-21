package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.User;
import com.bavithbhargav.chatservice.repositories.UserRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        return constructUserDTO(userFromDB);
    }

    private User constructUserDTO(User userFromDB) {
        // Construct the User object without the password attribute
        return User.builder()
                .userId(userFromDB.getUserId())
                .name(userFromDB.getName())
                .email(userFromDB.getEmail())
                .profilePicUrl(userFromDB.getProfilePicUrl())
                .createdDate(userFromDB.getCreatedDate())
                .updatedDate(userFromDB.getUpdatedDate())
                .build();
    }

}
