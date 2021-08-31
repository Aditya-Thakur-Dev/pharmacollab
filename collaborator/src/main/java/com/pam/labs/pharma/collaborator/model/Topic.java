package com.pam.labs.pharma.collaborator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private String topicId;
    private String title;
    private String shortDescription;
    private String status;
    private String departmentId;
    private String diseaseId;
    private Date created;
    private Date updated;
}
