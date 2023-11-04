package com.bavithbhargav.chatservice.events.models;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupEventType;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupUpdateType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GroupEvent extends ApplicationEvent {

    private final Group group;

    private final GroupEventType groupEventType;

    private final GroupUpdateType groupUpdateType;

    public GroupEvent(Object source, Group group, GroupEventType groupEventType, GroupUpdateType groupUpdateType) {
        super(source);
        this.group = group;
        this.groupEventType = groupEventType;
        this.groupUpdateType = groupUpdateType;
    }

}
