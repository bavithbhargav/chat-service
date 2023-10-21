package com.bavithbhargav.chatservice.controllers;

import com.bavithbhargav.chatservice.collections.Message;
import com.bavithbhargav.chatservice.repositories.MessageRepository;
import com.bavithbhargav.chatservice.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chatroom/message")
    @SendTo("/chatroom/public")
    public Message receiveChatRoomMessage(@Payload Message message) {
        message.setMessageId(MongoDBUtils.generateRandomPrimaryID(10));
        message.setCreatedDate(new Date());
        messageRepository.save(message);
        return message;
    }

    @MessageMapping("/private/message")
    public Message receivePersonalMessage(@Payload Message message) {
        message.setMessageId(MongoDBUtils.generateRandomPrimaryID(10));
        message.setCreatedDate(new Date());
        messageRepository.save(message);
        publishToSenderAndReceiverTopics(message);
        return message;
    }

    @MessageMapping("/group/message")
    public Message receiveGroupMessage(@Payload Message message) {
        message.setMessageId(MongoDBUtils.generateRandomPrimaryID(10));
        message.setCreatedDate(new Date());
        messageRepository.save(message);
        String groupTopic = "/group/" + message.getReceiverInfo().getReceiverId() + "/private";
        messagingTemplate.convertAndSend(groupTopic, message);
        return message;
    }

    private void publishToSenderAndReceiverTopics(Message message) {
        String receiverTopic = "/user/" + message.getReceiverInfo().getReceiverId() + "/private";
        String senderTopic = "/user/" + message.getSenderInfo().getSenderId() + "/private";
        messagingTemplate.convertAndSend(receiverTopic, message);
        messagingTemplate.convertAndSend(senderTopic, message);
    }

}
