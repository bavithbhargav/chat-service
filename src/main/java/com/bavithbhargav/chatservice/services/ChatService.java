package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupUpdateType;

public interface ChatService {

    Group createGroup(Group group);

    Group updateGroup(GroupUpdateType requestType, Group group);

    void deleteGroup(Group group);

}
