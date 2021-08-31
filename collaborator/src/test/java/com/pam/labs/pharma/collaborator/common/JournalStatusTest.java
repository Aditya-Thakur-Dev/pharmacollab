package com.pam.labs.pharma.collaborator.common;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JournalStatusTest {
    @Test
    public void enumValues_shouldReturnProperCode(){
        assertEquals("T", JournalStatus.TODO.getCode());
        assertEquals("P", JournalStatus.IN_PROGRESS.getCode());
        assertEquals("I", JournalStatus.IN_REVIEW.getCode());
        assertEquals("R", JournalStatus.REJECTED.getCode());
        assertEquals("C", JournalStatus.COMPLETED.getCode());
    }

    @Test
    public void getCollabTopicStatusCodeByCollabTopicStatus_shouldReturnCode_whenValidStatus(){
        assertEquals("P", JournalStatus.getCollabTopicStatusCodeByCollabTopicStatus("IN_PROGRESS"));
    }

    @Test
    public void getCollabTopicStatusCodeByCollabTopicStatus_shouldReturnNull_whenInvalidStatus(){
        assertNull(JournalStatus.getCollabTopicStatusCodeByCollabTopicStatus("INVALID"));
    }

    @Test
    public void getJournalStatusByCode_shouldReturnStatus_whenValidCode(){
        assertEquals("TODO", JournalStatus.getJournalStatusByCode("T"));
    }

    @Test
    public void getJournalStatusByCode_shouldReturnNull_whenInvalidCode(){
        assertNull(JournalStatus.getJournalStatusByCode("A"));
    }
}
