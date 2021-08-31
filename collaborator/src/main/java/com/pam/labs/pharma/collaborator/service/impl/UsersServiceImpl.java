package com.pam.labs.pharma.collaborator.service.impl;

import com.pam.labs.pharma.collaborator.common.UserRoles;
import com.pam.labs.pharma.collaborator.entity.ApplicationUsersDTO;
import com.pam.labs.pharma.collaborator.model.User;
import com.pam.labs.pharma.collaborator.repository.ApplicationUsersRepository;
import com.pam.labs.pharma.collaborator.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UsersServiceImpl implements UsersService {

    public static final String DEFAULT_PASSWORD = "RESET_PASSWORD";

    private final ApplicationUsersRepository applicationUsersRepository;

    @Override
    public List<User> getCollaboratorsByAdminUserId(String adminUserId) {
        List<ApplicationUsersDTO> collaborators = applicationUsersRepository.getCollaboratorsByAdminUserId(adminUserId);
        return CollectionUtils.isNotEmpty(collaborators)?
                collaborators.stream()
                .map(this::getUser)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public User getUserByUserId(String userId) {
        return applicationUsersRepository.findById(userId).map(this::getUser).orElse(null);
    }

    @Override
    public List<ApplicationUsersDTO> createNewUsers(List<User> users) {
        if(CollectionUtils.isNotEmpty(users)){
            List<ApplicationUsersDTO> applicationUsersDTOs = users.stream()
                    .filter(user -> StringUtils.isNoneBlank(user.getUserName(), user.getDepartmentId()) && CollectionUtils.isNotEmpty(user.getUserRoles()))
                    .map(dto -> getApplicationUsersDTO(dto, new Date()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(applicationUsersDTOs)){
                return applicationUsersRepository.saveAll(applicationUsersDTOs);
            }
        }
        return Collections.emptyList();
    }

    private ApplicationUsersDTO getApplicationUsersDTO(User user, Date createDate) {
        ApplicationUsersDTO applicationUsersDTO = new ApplicationUsersDTO();
        applicationUsersDTO.setUserId(getUserId(user.getUserId()));
        applicationUsersDTO.setDepartmentId(user.getDepartmentId());
        applicationUsersDTO.setUserName(user.getUserName());
        applicationUsersDTO.setUserRole(String.join(",", getUserRoleCodes(user.getUserRoles())));
        applicationUsersDTO.setPassword(DEFAULT_PASSWORD);
        applicationUsersDTO.setCreated(createDate);
        applicationUsersDTO.setUpdated(new Date());
        return applicationUsersDTO;
    }

    private String getUserId(String userId) {
        return StringUtils.isNotBlank(userId)? userId :
                StringUtils.join("USER_ID_", applicationUsersRepository.getUserIdSeqNextValue().toPlainString());
    }

    private User getUser(ApplicationUsersDTO applicationUsersDTO) {
        List<String> userRoles = StringUtils.isNotBlank(applicationUsersDTO.getUserRole())?
                Stream.of(applicationUsersDTO.getUserRole().split(",")).collect(Collectors.toList()) :
                Collections.emptyList();
        User user = new User();
        user.setUserId(applicationUsersDTO.getUserId());
        user.setUserName(applicationUsersDTO.getUserName());
        user.setUserRoles(getUserRoles(userRoles));
        user.setDepartmentId(applicationUsersDTO.getDepartmentId());
        return user;
    }

    private List<String> getUserRoles(List<String> userRoles) {
        return userRoles.stream()
                .map(UserRoles::getUserRoleByCode)
                .collect(Collectors.toList());
    }

    private List<String> getUserRoleCodes(List<String> userRoles) {
        return userRoles.stream()
                .map(UserRoles::getUserRoleCodeByUserRole)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
