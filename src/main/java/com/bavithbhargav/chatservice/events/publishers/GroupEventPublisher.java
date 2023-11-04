package com.bavithbhargav.chatservice.events.publishers;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupUpdateType;
import com.bavithbhargav.chatservice.events.models.GroupEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupEventType.*;

@Component
public class GroupEventPublisher {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishGroupCreationEvent(final Group group) {
        GroupEvent groupEvent = new GroupEvent(this, group, CREATE_GROUP, null);
        applicationEventPublisher.publishEvent(groupEvent);
    }

    public void publishGroupUpdationEvent(final Group group, final GroupUpdateType groupUpdateType) {
        GroupEvent groupEvent = new GroupEvent(this, group, UPDATE_GROUP, groupUpdateType);
        applicationEventPublisher.publishEvent(groupEvent);
    }

    public void publishGroupDeletionEvent(final Group group) {
        GroupEvent groupEvent = new GroupEvent(this, group, DELETE_GROUP, null);
        applicationEventPublisher.publishEvent(groupEvent);
    }

}
