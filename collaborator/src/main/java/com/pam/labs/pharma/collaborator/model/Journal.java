package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Journal {
    private String journalId;
    private String topicId;
    private String userId;
    private String journalName;
    private String type;
    ExploratoryDiscovery exploratoryDiscovery;
    LaterStageDiscovery laterStageDiscovery;
    PreclinicalTrial preclinicalTrial;
}