package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.LaterStageDiscoveryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
@Transactional
public interface LaterStageDiscoveryRepository extends JpaRepository<LaterStageDiscoveryDTO, String> {
    @Query(value = "select later_stage_discovery_id_seq.nextval from dual", nativeQuery = true)
    BigDecimal getDiscoveryIdSeqNextValue();

    void deleteAllByJournalId(String journalId);
}
