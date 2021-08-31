package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseDetail {
    private String diseaseId;
    private String diseaseName;
    private String description;
    private String diseaseOperationFile;
    private String targetFile;
    private String hypothesisFile;
    private String status;
}
