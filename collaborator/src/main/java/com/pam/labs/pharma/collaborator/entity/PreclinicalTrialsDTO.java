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
@Table(name = "preclinical_trials")
public class PreclinicalTrialsDTO implements Serializable {
    @Id
    @Column(name = "trial_id")
    private String trialId;

    @Column(name = "journal_id")
    private String journalId;

    @Column(name = "sample_id")
    private String sampleId;

    @Column(name = "acceptance_criteria_file")
    private String acceptanceCriteriaFile;

    @Column(name = "trial_method")
    private String trialMethod;

    @Column(name = "result")
    private String result;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
