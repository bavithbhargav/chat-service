package com.bavithbhargav.chatservice.events.models;

import com.bavithbhargav.chatservice.collections.Group;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GroupCreationEvent extends ApplicationEvent {

    private final Group group;

    public GroupCreationEvent(Object source, Group group) {
        super(source);
        this.group = group;
    }

}
