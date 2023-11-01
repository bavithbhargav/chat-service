package com.bavithbhargav.chatservice.collections;

import com.bavithbhargav.chatservice.constants.ChatServiceConstants.MessageType;
import com.bavithbhargav.chatservice.models.ChatRoomInfo;
import com.bavithbhargav.chatservice.models.GroupInfo;
import com.bavithbhargav.chatservice.models.ReceiverInfo;
import com.bavithbhargav.chatservice.models.SenderInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message")
public class Message {

    @Id
    private String messageId;

    @NotBlank(message = "Content is required")
    private String content;

    private MessageType messageType;

    @Valid
    private SenderInfo senderInfo;

    @Valid
    private ReceiverInfo receiverInfo;

    @Valid
    private ChatRoomInfo chatRoomInfo;

    @Valid
    private GroupInfo groupInfo;

    private Date createdDate;

}
