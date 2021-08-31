package com.pam.labs.pharma.collaborator.common;

public enum TrialStatus {
    SUCCESSFUL("S"),
    UNSUCCESSFUL("U");

    private final String code;

    TrialStatus(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

}
