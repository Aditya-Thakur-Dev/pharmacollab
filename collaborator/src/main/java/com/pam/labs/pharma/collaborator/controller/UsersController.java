package com.pam.labs.pharma.collaborator.controller;

import com.pam.labs.pharma.collaborator.common.UserRoles;
import com.pam.labs.pharma.collaborator.entity.ApplicationUsersDTO;
import com.pam.labs.pharma.collaborator.model.User;
import com.pam.labs.pharma.collaborator.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins={"http://localhost:3000"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Validated
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/getCollaboratorsByAdminUserId")
    public ResponseEntity<List<User>> getCollaboratorsByAdminUserId(@Valid @RequestParam @NotBlank String adminUserId) {
        List<User> usersByDepartmentId = usersService.getCollaboratorsByAdminUserId(adminUserId);
        return CollectionUtils.isNotEmpty(usersByDepartmentId)? new ResponseEntity<>(usersByDepartmentId, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getUserByUserId")
    public ResponseEntity<User> getUserByUserId(@Valid @RequestParam @NotBlank String userId) {
        User usersByDepartmentId = usersService.getUserByUserId(userId);
        return Objects.nonNull(usersByDepartmentId)? new ResponseEntity<>(usersByDepartmentId, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/createNewUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ApplicationUsersDTO>> createNewUsers(@Valid @RequestBody @NotEmpty List<User> users) {
        List<ApplicationUsersDTO> savedApplicationUsersDTOs = usersService.createNewUsers(users);
        return CollectionUtils.isNotEmpty(savedApplicationUsersDTOs)? new ResponseEntity<>(savedApplicationUsersDTOs, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/getUserRoles")
    public ResponseEntity<List<String>> getUserRoles() {
        List<String> userRoles = Stream.of(UserRoles.values())
                .map(UserRoles::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }
}
