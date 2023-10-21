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
public class ReceiverInfo {

    @NotBlank(message = "receiverId is required")
    private String receiverId;

    @NotBlank(message = "receiverName is required")
    private String receiverName;

}
