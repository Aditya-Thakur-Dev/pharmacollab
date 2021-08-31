package com.pam.labs.pharma.collaborator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exploratory_discovery")
public class ExploratoryDiscoveryDTO implements Serializable {
    @Id
    @Column(name = "exploratory_discovery_id")
    private String exploratoryDiscoveryId;

    @Column(name = "journal_id")
    private String journalId;

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
