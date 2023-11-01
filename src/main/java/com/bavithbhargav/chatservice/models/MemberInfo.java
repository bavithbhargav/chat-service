package com.bavithbhargav.chatservice.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {

    @NotBlank(message = "memberId is required")
    private String userId;

    @NotBlank(message = "memberName is required")
    private String userName;

    private Date joiningDate;

}
