package com.pam.labs.pharma.collaborator.repository;

import com.pam.labs.pharma.collaborator.entity.ApplicationUsersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ApplicationUsersRepository extends JpaRepository<ApplicationUsersDTO, String> {

    @Query(value = "select user_id_seq.nextval from dual", nativeQuery = true)
    public BigDecimal getUserIdSeqNextValue();

    @Query("select applicationUsersDTO2 from ApplicationUsersDTO applicationUsersDTO " +
            "join ApplicationUsersDTO applicationUsersDTO2 on applicationUsersDTO2.departmentId = applicationUsersDTO.departmentId " +
            "where applicationUsersDTO.userId = :adminUserId " +
            "and applicationUsersDTO2.userRole = 'C'")
    List<ApplicationUsersDTO> getCollaboratorsByAdminUserId(String adminUserId);

    ApplicationUsersDTO findDepartmentIdByUserId(String userId);
}
