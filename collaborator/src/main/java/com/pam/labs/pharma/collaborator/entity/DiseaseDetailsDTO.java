package com.pam.labs.pharma.collaborator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disease_details")
public class DiseaseDetailsDTO implements Serializable {
    @Id
    @Column(name = "disease_id")
    private String diseaseId;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "description")
    private String description;

    @Column(name = "disease_operation_file")
    private String diseaseOperationFile;

    @Column(name = "target_file")
    private String targetFile;

    @Column(name = "hypothesis_file")
    private String hypothesisFile;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
