package com.pam.labs.pharma.collaborator.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserRolesTest {
    @Test
    public void enumValues_shouldReturnProperCode(){
        assertEquals("A", UserRoles.ADMIN.getCode());
        assertEquals("C", UserRoles.COLLABORATOR.getCode());
    }

    @Test
    public void getUserRoleCodeByUserRole_shouldReturnCode_whenValidRole(){
        assertEquals("A", UserRoles.getUserRoleCodeByUserRole("ADMIN"));
    }

    @Test
    public void getUserRoleCodeByUserRole_shouldReturnNull_whenInvalidRole(){
        assertNull(UserRoles.getUserRoleCodeByUserRole("INVALID"));
    }

    @Test
    public void getUserRoleByCode_shouldReturnRole_whenValidCode(){
        assertEquals("ADMIN", UserRoles.getUserRoleByCode("A"));
    }

    @Test
    public void getUserRoleByCode_shouldReturnNull_whenInvalidCode(){
        assertNull(UserRoles.getUserRoleByCode("I"));
    }
}
