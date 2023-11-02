package com.bavithbhargav.chatservice.events.publishers;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.events.models.GroupCreationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GroupCreationEventPublisher {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishGroupCreationEvent(final Group group) {
        GroupCreationEvent groupCreationEvent = new GroupCreationEvent(this, group);
        applicationEventPublisher.publishEvent(groupCreationEvent);
    }

}
