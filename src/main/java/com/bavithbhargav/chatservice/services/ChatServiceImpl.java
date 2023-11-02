package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.events.publishers.GroupCreationEventPublisher;
import com.bavithbhargav.chatservice.repositories.GroupRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

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


}
