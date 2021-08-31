package com.pam.labs.pharma.collaborator.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JournalTypesTest {
    @Test
    public void enumValues_shouldReturnProperCode(){
        assertEquals("E", JournalTypes.EXPLORATORY_DISCOVERY.getCode());
        assertEquals("L", JournalTypes.LATER_STAGE_DISCOVERY.getCode());
        assertEquals("P", JournalTypes.PRECLINICAL_TRIAL.getCode());
    }

    @Test
    public void getJournalTypeCodeByJournalType_shouldReturnCode_whenValidType(){
        assertEquals("E", JournalTypes.getJournalTypeCodeByJournalType("EXPLORATORY_DISCOVERY"));
    }

    @Test
    public void getJournalTypeCodeByJournalType_shouldReturnNull_whenInvalidtype(){
        assertNull(JournalTypes.getJournalTypeCodeByJournalType("INVALID"));
    }

    @Test
    public void getJournalTypeByCode_shouldReturnType_whenValidCode(){
        assertEquals("EXPLORATORY_DISCOVERY", JournalTypes.getJournalTypeByCode("E"));
    }

    @Test
    public void getJournalTypeByCode_shouldReturnNull_whenInvalidCode(){
        assertNull(JournalTypes.getJournalTypeByCode("A"));
    }
}
