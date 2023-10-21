package com.bavithbhargav.chatservice.repositories;

import com.bavithbhargav.chatservice.collections.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
}
