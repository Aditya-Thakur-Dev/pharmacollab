package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.TopicAllocationsDTO;
import com.pam.labs.pharma.collaborator.entity.TopicAllocationsPK;
import com.pam.labs.pharma.collaborator.entity.TopicsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicAllocationsRepository extends JpaRepository<TopicAllocationsDTO, TopicAllocationsPK> {
    List<TopicAllocationsDTO> findAllByIdTopicId(String topicId);
}
