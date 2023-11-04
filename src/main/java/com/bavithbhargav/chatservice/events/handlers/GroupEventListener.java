package com.bavithbhargav.chatservice.events.handlers;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.collections.User;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupUpdateType;
import com.bavithbhargav.chatservice.events.models.GroupEvent;
import com.bavithbhargav.chatservice.models.GroupInfo;
import com.bavithbhargav.chatservice.models.MemberInfo;
import com.bavithbhargav.chatservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupEventType.CREATE_GROUP;

@Component
public class GroupEventListener {

    @Autowired
    private UserRepository userRepository;

    @EventListener
    @Async
    public void handleGroupEvent(GroupEvent groupEvent) {
        Group group = groupEvent.getGroup();
        if (groupEvent.getGroupEventType() == CREATE_GROUP) {
            handleCreateGroupEvent(group);
        } else {
            handleUpdateGroupEvent(group, groupEvent.getGroupUpdateType());
        }
    }

    private void handleUpdateGroupEvent(Group group, GroupUpdateType groupUpdateType) {
        List<String> userIds = group.getMembers().stream()
                .map(MemberInfo::getUserId)
                .toList();
        List<User> usersFromDB = userRepository.findAllById(userIds);
        GroupInfo groupInfoObj = new GroupInfo(group.getGroupId(), group.getGroupName());
        switch (groupUpdateType) {
            case UPDATE_GROUP_NAME -> {
                for (User user : usersFromDB) {
                    for (GroupInfo groupInfo : user.getUserGroups()) {
                        if (groupInfo.getGroupId().equals(group.getGroupId())) {
                            groupInfo.setGroupName(group.getGroupName());
                        }
                    }
                }
            }
            case ADD_USERS -> {
                for (User user : usersFromDB) {
                    user.getUserGroups().add(groupInfoObj);
                }
            }
            case REMOVE_USERS -> {
                for (User user : usersFromDB) {
                    user.getUserGroups().remove(groupInfoObj);
                }
            }
        }
        userRepository.saveAll(usersFromDB);
    }

    private void handleCreateGroupEvent(Group group) {
        List<String> userIds = group.getMembers().stream()
                .map(MemberInfo::getUserId)
                .toList();
        List<User> usersFromDB = userRepository.findAllById(userIds);
        List<User> updatedUsers = usersFromDB.stream()
                .map(user -> {
                    GroupInfo groupInfo = new GroupInfo(group.getGroupId(), group.getGroupName());
                    user.getUserGroups().add(groupInfo);
                    return user;
                })
                .toList();
        userRepository.saveAll(updatedUsers);
    }

}