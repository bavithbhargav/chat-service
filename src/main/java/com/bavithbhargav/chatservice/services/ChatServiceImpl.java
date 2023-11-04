package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupUpdateType;
import com.bavithbhargav.chatservice.events.publishers.GroupEventPublisher;
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
    private GroupEventPublisher groupEventPublisher;

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
        groupEventPublisher.publishGroupCreationEvent(savedGroup);
        return savedGroup;
    }

    @Override
    public Group updateGroup(GroupUpdateType requestType, Group groupFromUser) {
        Group groupFromDB = mongoTemplate.findOne(
                Query.query(Criteria.where("groupId").is(groupFromUser.getGroupId())),
                Group.class
        );
        if (groupFromDB == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Requested resource not found");
        }
        switch (requestType) {
            case ADD_USERS -> {
                List<MemberInfo> usersToBeAdded = groupFromUser.getMembers()
                        .stream()
                        .filter(memberInfo -> memberInfo.getUserId() != null && memberInfo.getUserName() != null)
                        .filter(memberInfo -> !groupFromDB.getMembers().contains(memberInfo))
                        .toList();
                groupFromDB.getMembers().addAll(usersToBeAdded);
                groupFromUser.setMembers(usersToBeAdded);
            }
            case REMOVE_USERS -> {
                List<MemberInfo> usersToBeRemoved = groupFromUser.getMembers()
                        .stream()
                        .filter(memberInfo -> memberInfo.getUserId() != null && memberInfo.getUserName() != null)
                        .toList();
                groupFromDB.getMembers().removeAll(usersToBeRemoved);
                groupFromUser.setMembers(usersToBeRemoved);
            }
            case UPDATE_GROUP_NAME -> {
                boolean groupNameAlreadyExists = groupRepository.findByGroupName(groupFromUser.getGroupName()) != null;
                if (groupNameAlreadyExists) {
                    throw new HttpClientErrorException(HttpStatus.CONFLICT, "Requested name already exists");
                }
                groupFromDB.setGroupName(groupFromUser.getGroupName());
            }
        }
        Group savedGroup = groupRepository.save(groupFromDB);
        groupEventPublisher.publishGroupUpdationEvent(groupFromUser, requestType);
        return savedGroup;
    }

    @Override
    public void deleteGroup(Group group) {
        groupRepository.deleteById(group.getGroupId());
        groupEventPublisher.publishGroupDeletionEvent(group);
    }

}
