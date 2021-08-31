package com.pam.labs.pharma.collaborator.common;

import java.util.stream.Stream;

public enum JournalStatus {
    TODO("T"),
    IN_PROGRESS("P"),
    IN_REVIEW("I"),
    REJECTED("R"),
    COMPLETED("C");

    private final String code;

    JournalStatus(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static String getCollabTopicStatusCodeByCollabTopicStatus(String collaboratorTopicStatus){
        return Stream.of(JournalStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(collaboratorTopicStatus))
                .findFirst()
                .map(status -> status.code)
                .orElse(null);
    }

    public static String getJournalStatusCodeByJournalStatus(String journalStatus){
        return Stream.of(JournalStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(journalStatus))
                .findFirst()
                .map(status -> status.code)
                .orElse(null);
    }

    public static String getJournalStatusByCode(String code){
        return Stream.of(JournalStatus.values())
                .filter(status -> status.code.equalsIgnoreCase(code))
                .findFirst()
                .map(Enum::name)
                .orElse(null);
    }
}
