package com.pam.labs.pharma.collaborator.service;

import com.pam.labs.pharma.collaborator.model.Topic;
import com.pam.labs.pharma.collaborator.model.User;

import java.util.List;

public interface ReportsService {
    List<Topic> getOngoingTopicsStatus(User user);

    List<Topic> getCompletedTopics(User user);
}
