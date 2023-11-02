package com.bavithbhargav.chatservice.constants;

public class ChatServiceConstants {

    public enum MessageType {
        CHATROOM,
        GROUP,
        PRIVATE
    }

    public enum GroupRequestType {
        ADD_USERS,
        REMOVE_USERS,
        UPDATE_GROUP_NAME
    }

}
