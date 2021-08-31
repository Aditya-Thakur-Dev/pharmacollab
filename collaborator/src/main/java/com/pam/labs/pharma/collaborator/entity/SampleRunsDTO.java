package com.pam.labs.pharma.collaborator.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@Entity
@Table(name = "SAMPLE_RUNS")
public class SampleRunsDTO implements Serializable {
    @Id
    @Column(name = "TEST_RUN_SEQ")
    @SequenceGenerator(name = "TEST_RUN_INCREMENTER", sequenceName = "TEST_RUN_INCREMENTER", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_RUN_INCREMENTER")
    private int testRunSequence;

    @Column(name = "TEST_RUN_TIME")
    private Date testRuntime;

    @Column(name = "TEST_RUN_USER")
    private String testRunUser;
}
