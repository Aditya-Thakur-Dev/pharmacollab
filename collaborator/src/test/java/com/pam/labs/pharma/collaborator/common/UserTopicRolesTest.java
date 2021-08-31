package com.pam.labs.pharma.collaborator.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTopicRolesTest {
    @Test
    public void enumValues_shouldReturnProperCode(){
        assertEquals("R", UserTopicRoles.REVIEWER.getCode());
        assertEquals("C", UserTopicRoles.COLLABORATOR.getCode());
    }

    @Test
    public void getUserTopicRoleCodeByUserTopicRole_shouldReturnCode_whenValidRole(){
        assertEquals("R", UserTopicRoles.getUserTopicRoleCodeByUserTopicRole("REVIEWER"));
    }

    @Test
    public void getUserTopicRoleCodeByUserTopicRole_shouldReturnNull_whenInvalidRole(){
        assertNull(UserTopicRoles.getUserTopicRoleCodeByUserTopicRole("INVALID"));
    }
}
