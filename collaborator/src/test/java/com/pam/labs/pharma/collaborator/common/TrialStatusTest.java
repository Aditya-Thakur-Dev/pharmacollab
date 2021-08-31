package com.pam.labs.pharma.collaborator.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrialStatusTest {
    @Test
    public void enumValues_shouldReturnProperCode(){
        assertEquals("S", TrialStatus.SUCCESSFUL.getCode());
        assertEquals("U", TrialStatus.UNSUCCESSFUL.getCode());
    }
}
