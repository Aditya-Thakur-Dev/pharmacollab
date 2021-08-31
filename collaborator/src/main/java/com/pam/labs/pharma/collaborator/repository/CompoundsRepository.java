package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.CompoundsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CompoundsRepository extends JpaRepository<CompoundsDTO, String> {
    @Query(value = "select compound_id_seq.nextval from dual", nativeQuery = true)
    public BigDecimal getCompoundIdSeqNextValue();
}
