package com.pam.labs.pharma.collaborator.service.impl;

import com.pam.labs.pharma.collaborator.model.Topic;
import com.pam.labs.pharma.collaborator.model.User;
import com.pam.labs.pharma.collaborator.service.ReportsService;

import java.util.List;

public class ReportsServiceImpl implements ReportsService {
    @Override
    public List<Topic> getOngoingTopicsStatus(User user) {
        return null;
    }

    @Override
    public List<Topic> getCompletedTopics(User user) {
        return null;
    }
}
