package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.TopicsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TopicsRepository extends JpaRepository<TopicsDTO, String> {

    @Query(value = "select topic_id_seq.nextval from dual", nativeQuery = true)
    public BigDecimal getTopicIdSeqNextValue();

    @Query("select topicsDTO from TopicsDTO topicsDTO " +
            "join TopicAllocationsDTO topicAllocationsDTO on topicAllocationsDTO.id.topicId = topicsDTO.topicId " +
            "where topicAllocationsDTO.id.userId = :userId ")
    List<TopicsDTO> getAllTopicsByUserId(@Param("userId") String userId);

    List<TopicsDTO> findAllByDepartmentId(String departmentId);
}
