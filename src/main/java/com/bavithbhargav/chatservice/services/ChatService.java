package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupRequestType;

public interface ChatService {

    Group createGroup(Group group);

    Group updateGroup(GroupRequestType requestType, Group group);

}
