package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicAllocation {
    private String topicId;
    private String userId;
    private List<String> topicRole;
}
