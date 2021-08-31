package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaterStageDiscovery {
    private String laterStageDiscoveryId;
    private String journalId;
    private String compoundsFile;
    private String fitForTarget;
    private String status;
}
