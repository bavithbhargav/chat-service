package com.bavithbhargav.chatservice.repositories;

import com.bavithbhargav.chatservice.collections.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Group findByGroupName(String groupName);

}
