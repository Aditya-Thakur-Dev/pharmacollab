package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.JournalsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public interface JournalsRepository extends JpaRepository<JournalsDTO, String> {
    @Query(value = "select journal_id_seq.nextval from dual", nativeQuery = true)
    public BigDecimal getJournalIdSeqNextValue();

    List<JournalsDTO> findAllByJournalId(String journalId);

    List<JournalsDTO> findAllByTopicIdAndUserId(String topicId, String userId);

    void deleteAllByTopicIdAndUserId(String topicId, String userId);

    JournalsDTO findByJournalId(String journalId);

    void deleteAllByJournalId(String journalId);

    @Query("select journalsDTO, ED, LSD, PCT from JournalsDTO journalsDTO " +
            "left join ExploratoryDiscoveryDTO ED on ED.journalId = journalsDTO.journalId " +
            "left join LaterStageDiscoveryDTO LSD on LSD.journalId = journalsDTO.journalId " +
            "left join PreclinicalTrialsDTO PCT on PCT.journalId = journalsDTO.journalId " +
            "where journalsDTO.topicId = :topicId " +
            "and journalsDTO.userId = :userId ")
    List<List<Object>> getAllJournals(@Param("topicId") String topicId, @Param("userId") String userId);
}
