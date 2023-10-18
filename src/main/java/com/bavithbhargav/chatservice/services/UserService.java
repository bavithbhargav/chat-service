package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.User;
import org.springframework.web.client.HttpClientErrorException;

public interface UserService {

    User registerUser(User user) throws HttpClientErrorException;

    User loginUser(User user);

}
