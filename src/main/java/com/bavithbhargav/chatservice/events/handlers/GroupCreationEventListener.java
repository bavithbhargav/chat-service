package com.bavithbhargav.chatservice.events.handlers;

import com.bavithbhargav.chatservice.events.models.GroupCreationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GroupCreationEventListener {

    @EventListener
    @Async
    public void handleGroupCreationEvent(GroupCreationEvent groupCreationEvent) {
        // TODO: Implement the group creation handler
    }

}