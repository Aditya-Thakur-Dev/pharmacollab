package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreclinicalTrial {
    private String trialId;
    private String journalId;
    private String sampleId;
    private String acceptanceCriteriaFile;
    private String trialMethod;
    private String result;
    private String status;
}
