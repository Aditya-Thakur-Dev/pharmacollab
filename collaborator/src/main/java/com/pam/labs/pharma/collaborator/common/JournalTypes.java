package com.pam.labs.pharma.collaborator.common;

import java.util.stream.Stream;

public enum JournalTypes {
    EXPLORATORY_DISCOVERY("E"),
    LATER_STAGE_DISCOVERY("L"),
    PRECLINICAL_TRIAL("P");

    private final String code;

    JournalTypes(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static String getJournalTypeCodeByJournalType(String journalType){
        return Stream.of(JournalTypes.values())
                .filter(type -> type.name().equalsIgnoreCase(journalType))
                .findFirst()
                .map(type -> type.code)
                .orElse(null);
    }

    public static String getJournalTypeByCode(String code){
        return Stream.of(JournalTypes.values())
                .filter(journalType -> journalType.code.equalsIgnoreCase(code))
                .findFirst()
                .map(Enum::name)
                .orElse(null);
    }
}
