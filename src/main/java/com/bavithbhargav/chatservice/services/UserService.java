package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.User;

public interface UserService {

    User registerUser(User user);

    User loginUser(User user);

}
