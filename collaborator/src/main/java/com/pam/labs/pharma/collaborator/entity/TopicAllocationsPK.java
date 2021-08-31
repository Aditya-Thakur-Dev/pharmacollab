package com.pam.labs.pharma.collaborator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TopicAllocationsPK implements Serializable {
    @Column(name = "topic_id")
    private String topicId;

    @Column(name = "user_id")
    private String userId;

}
