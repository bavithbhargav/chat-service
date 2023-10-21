package com.bavithbhargav.chatservice.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenderInfo {

    @NotBlank(message = "senderId is required")
    private String senderId;

    @NotBlank(message = "senderName is required")
    private String senderName;

}
