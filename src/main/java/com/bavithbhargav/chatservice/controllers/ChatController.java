package com.bavithbhargav.chatservice.controllers;

import com.bavithbhargav.chatservice.collections.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chatroom/message")
    @SendTo("/chatroom/public")
    public Message receiveChatRoomMessage(@Payload Message message) {
        message.setCreatedDate(new Date());
        return message;
    }

}
