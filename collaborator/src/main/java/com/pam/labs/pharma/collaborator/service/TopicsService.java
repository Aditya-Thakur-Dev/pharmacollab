package com.pam.labs.pharma.collaborator.service;

import com.pam.labs.pharma.collaborator.model.Topic;
import com.pam.labs.pharma.collaborator.model.TopicAllocation;
import com.pam.labs.pharma.collaborator.model.User;

import java.util.List;

public interface TopicsService {
    List<Topic> getTopicsByUserId(String userId);

    List<User> getUsersByTopicId(String topicId);

    List<Topic> getTopicsByAdminUserId(String adminUserId);

    boolean allocateAndStartTopic(List<TopicAllocation> topicAllocations);

    Topic createNewTopic(Topic topic);
}
