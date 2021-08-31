package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.SampleRunsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRunsRepository extends JpaRepository<SampleRunsDTO, Integer> {

}
