package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Message;
import com.bavithbhargav.chatservice.repositories.MessageRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Message processChatRoomMessage(Message message) {
        message.setMessageId(MongoDBUtils.generateRandomPrimaryID(10));
        message.setCreatedDate(new Date());
        Message savedMessage = messageRepository.save(message);
        messagingTemplate.convertAndSend("/chatroom/public", savedMessage);
        return savedMessage;
    }

    @Override
    public Message processPrivateMessage(Message message) {
        message.setMessageId(MongoDBUtils.generateRandomPrimaryID(10));
        message.setCreatedDate(new Date());
        Message savedMessage = messageRepository.save(message);
        publishToSenderAndReceiverTopics(message);
        return savedMessage;
    }

    @Override
    public Message processGroupMessage(Message message) {
        message.setMessageId(MongoDBUtils.generateRandomPrimaryID(10));
        message.setCreatedDate(new Date());
        Message savedMessage = messageRepository.save(message);
        String groupTopic = "/group/" + message.getReceiverInfo().getReceiverId() + "/private";
        messagingTemplate.convertAndSend(groupTopic, message);
        return savedMessage;
    }

    private void publishToSenderAndReceiverTopics(Message message) {
        String receiverTopic = "/user/" + message.getReceiverInfo().getReceiverId() + "/private";
        String senderTopic = "/user/" + message.getSenderInfo().getSenderId() + "/private";
        messagingTemplate.convertAndSend(receiverTopic, message);
        messagingTemplate.convertAndSend(senderTopic, message);
    }

}
