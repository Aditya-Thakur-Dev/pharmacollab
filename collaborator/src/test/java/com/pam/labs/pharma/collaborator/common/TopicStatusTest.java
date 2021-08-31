package com.pam.labs.pharma.collaborator.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TopicStatusTest {
    @Test
    public void enumValues_shouldReturnProperCode(){
        assertEquals("N", TopicStatus.NOT_STARTED.getCode());
        assertEquals("A", TopicStatus.ASSIGNED.getCode());
        assertEquals("P", TopicStatus.IN_PROGRESS.getCode());
        assertEquals("R", TopicStatus.REJECTED.getCode());
        assertEquals("C", TopicStatus.COMPLETED.getCode());
    }
}
