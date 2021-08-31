package com.pam.labs.pharma.collaborator.service;

import com.pam.labs.pharma.collaborator.entity.ApplicationUsersDTO;
import com.pam.labs.pharma.collaborator.model.User;

import java.util.List;

public interface UsersService {

    List<User> getCollaboratorsByAdminUserId(String adminUserId);

    User getUserByUserId(String userId);

    List<ApplicationUsersDTO> createNewUsers(List<User> users);
}
