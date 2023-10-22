package com.bavithbhargav.chatservice.services;

import com.bavithbhargav.chatservice.collections.Message;

public interface ChatService {

    Message processChatRoomMessage(Message message);

    Message processPrivateMessage(Message message);

    Message processGroupMessage(Message message);

}
