package com.bavithbhargav.chatservice.constants;

public class ChatServiceConstants {

    public enum MessageType {
        CHATROOM,
        GROUP,
        PRIVATE
    }

    public enum GroupEventType {
        CREATE_GROUP,
        UPDATE_GROUP,
        DELETE_GROUP
    }

    public enum GroupUpdateType {
        ADD_USERS,
        REMOVE_USERS,
        UPDATE_GROUP_NAME
    }

}
