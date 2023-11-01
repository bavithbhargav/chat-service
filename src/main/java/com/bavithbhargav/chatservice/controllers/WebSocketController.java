package com.bavithbhargav.chatservice.controllers;

import com.bavithbhargav.chatservice.collections.Message;
import com.bavithbhargav.chatservice.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/chatroom/message")
    public Message receiveChatRoomMessage(@Payload Message message) {
        return webSocketService.processChatRoomMessage(message);
    }

    @MessageMapping("/private/message")
    public Message receivePersonalMessage(@Payload Message message) {
        return webSocketService.processPrivateMessage(message);
    }

    @MessageMapping("/group/message")
    public Message receiveGroupMessage(@Payload Message message) {
        return webSocketService.processGroupMessage(message);
    }

    @GetMapping("/group-chat")
    public String serveHTML() {
        return "pages/group-chat.html";
    }

}
