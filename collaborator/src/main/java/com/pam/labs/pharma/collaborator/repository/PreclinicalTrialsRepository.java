package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.PreclinicalTrialsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
@Transactional
public interface PreclinicalTrialsRepository extends JpaRepository<PreclinicalTrialsDTO, String> {
    @Query(value = "select trial_id_seq.nextval from dual", nativeQuery = true)
    BigDecimal getTrialIdSeqNextValue();

    void deleteAllByJournalId(String journalId);
}
