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
public class GroupInfo {

    @NotBlank(message = "groupId is required")
    private String groupId;

    @NotBlank(message = "groupName is required")
    private String groupName;

}
