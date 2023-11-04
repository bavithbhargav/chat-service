package com.bavithbhargav.chatservice.controllers;

import com.bavithbhargav.chatservice.collections.Group;
import com.bavithbhargav.chatservice.constants.ChatServiceConstants.GroupUpdateType;
import com.bavithbhargav.chatservice.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chat-service", produces = "application/json")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("api/v1/groups")
    public ResponseEntity<Group> createGroup(@RequestBody @Valid Group group) {
        return new ResponseEntity<>(chatService.createGroup(group), HttpStatus.CREATED);
    }

    @PatchMapping("api/v1/groups")
    public ResponseEntity<Group> updateGroup(@RequestBody Group group, @RequestParam GroupUpdateType requestType) {
        return new ResponseEntity<>(chatService.updateGroup(requestType, group), HttpStatus.OK);
    }

}
