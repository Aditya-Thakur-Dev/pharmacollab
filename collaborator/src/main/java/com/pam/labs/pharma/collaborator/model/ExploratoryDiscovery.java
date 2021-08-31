package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExploratoryDiscovery {
    private String exploratoryDiscoveryId;
    private String journalId;
    private String diseaseOperationFile;
    private String targetFile;
    private String hypothesisFile;
    private String status;
}
