package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupRequestType;
import com.bavithbhargav.chatservice.events.publishers.GroupCreationEventPublisher;
import com.bavithbhargav.chatservice.models.MemberInfo;
import com.bavithbhargav.chatservice.repositories.GroupRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    @Autowired
    private MongoTemplate mongoTemplate;

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
        Group groupFromDB = mongoTemplate.findOne(
                Query.query(Criteria.where("groupId").is(group.getGroupId())),
                Group.class
        );
        if (groupFromDB == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Requested resource not found");
        }
        switch (requestType) {
            case ADD_USERS -> {
                List<MemberInfo> usersToBeAdded = group.getMembers()
                        .stream()
                        .filter(memberInfo -> memberInfo.getUserId() != null && memberInfo.getUserName() != null)
                        .filter(memberInfo -> !groupFromDB.getMembers().contains(memberInfo))
                        .toList();
                groupFromDB.getMembers().addAll(usersToBeAdded);
            }
            case REMOVE_USERS -> {
                List<MemberInfo> usersToBeRemoved = group.getMembers()
                        .stream()
                        .filter(memberInfo -> memberInfo.getUserId() != null && memberInfo.getUserName() != null)
                        .toList();
                groupFromDB.getMembers().removeAll(usersToBeRemoved);
            }
            case UPDATE_GROUP_NAME -> {
                boolean groupNameAlreadyExists = groupRepository.findByGroupName(group.getGroupName()) != null;
                if (groupNameAlreadyExists) {
                    throw new HttpClientErrorException(HttpStatus.CONFLICT, "Requested name already exists");
                }
                groupFromDB.setGroupName(group.getGroupName());
            }
        }
        groupRepository.save(groupFromDB);
        return null;
    }


}
