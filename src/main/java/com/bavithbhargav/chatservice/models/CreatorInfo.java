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
public class CreatorInfo {

    @NotBlank(message = "creatorId is required")
    private String userId;

    @NotBlank(message = "creatorName is required")
    private String userName;

}
