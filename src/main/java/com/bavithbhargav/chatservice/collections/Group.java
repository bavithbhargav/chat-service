package com.bavithbhargav.chatservice.collections;

import com.bavithbhargav.chatservice.models.CreatorInfo;
import com.bavithbhargav.chatservice.models.MemberInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "group")
public class Group {

    @Id
    private String groupId;

    @NotBlank(message = "groupName is required")
    private String groupName;

    @Size(min = 2, message = "group must have at least 2 members")
    private List<MemberInfo> members;

    @NotNull
    @Valid
    private CreatorInfo creatorInfo;

    private Date creationDate;

}
