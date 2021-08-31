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
@Table(name = "later_stage_discovery")
public class LaterStageDiscoveryDTO implements Serializable {
    @Id
    @Column(name = "later_stage_discovery_id")
    private String laterStageDiscoveryId;

    @Column(name = "journal_id")
    private String journalId;

    @Column(name = "compounds_file")
    private String compoundsFile;

    @Column(name = "fit_for_target")
    private String fitForTarget;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
