package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User registerUser(User user);

    User loginUser(User user);

    Page<User> getAllUsers(Pageable pageable);

}
