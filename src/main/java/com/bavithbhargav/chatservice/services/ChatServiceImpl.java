package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupRequestType;
import com.bavithbhargav.chatservice.events.publishers.GroupCreationEventPublisher;
import com.bavithbhargav.chatservice.models.MemberInfo;
import com.bavithbhargav.chatservice.repositories.GroupRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupCreationEventPublisher groupCreationEventPublisher;

    @Override
    public Group createGroup(Group group) {
        Group groupFromDB = groupRepository.findByGroupName(group.getGroupName());
        if (groupFromDB != null) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Group name already exists");
        }
        group.setGroupId(MongoDBUtils.generateRandomPrimaryID(10));
        group.getMembers().forEach(memberInfo -> memberInfo.setJoiningDate(new Date()));
        group.setCreationDate(new Date());
        Group savedGroup = groupRepository.save(group);
        groupCreationEventPublisher.publishGroupCreationEvent(group);
        return savedGroup;
    }

    @Override
    public Group updateGroup(GroupRequestType requestType, Group group) {
        Group groupFromDB = groupRepository.findByGroupName(group.getGroupName());
        if (groupFromDB == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Requested resource not found");
        }
        switch (requestType) {
            case ADD_USERS -> {
                List<MemberInfo> usersToBeAdded = group.getMembers();
                groupFromDB.getMembers().addAll(
                        usersToBeAdded
                                .stream()
                                .filter(memberInfo -> memberInfo.getUserId() != null && memberInfo.getUserName() != null)
                                .toList()
                );
            }
            case REMOVE_USERS -> {
            }
            case UPDATE_GROUP_NAME -> {

            }
        }
        return null;
    }


}
