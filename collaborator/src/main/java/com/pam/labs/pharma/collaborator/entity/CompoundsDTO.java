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
@Table(name = "compounds")
public class CompoundsDTO implements Serializable {
    @Id
    @Column(name = "compound_id")
    private String compoundId;

    @Column(name = "compound_name")
    private String compoundName;

    @Column(name = "disease_ids")
    private String diseaseIds;

    @Column(name = "description_file")
    private String descriptionFile;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
