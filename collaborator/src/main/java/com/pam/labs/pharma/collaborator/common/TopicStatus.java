package com.pam.labs.pharma.collaborator.common;


public enum TopicStatus {
    NOT_STARTED("N"),
    ASSIGNED("A"),
    IN_PROGRESS("P"),
    REJECTED("R"),
    COMPLETED("C");

    private final String code;

    TopicStatus(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

}
