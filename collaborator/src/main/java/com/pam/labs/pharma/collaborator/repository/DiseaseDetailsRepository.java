package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.DiseaseDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DiseaseDetailsRepository extends JpaRepository<DiseaseDetailsDTO, String> {
    @Query(value = "select disease_id_seq.nextval from dual", nativeQuery = true)
    public BigDecimal getDiseaseIdSeqNextValue();
}
