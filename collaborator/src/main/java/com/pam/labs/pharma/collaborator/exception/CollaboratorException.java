package com.pam.labs.pharma.collaborator.exception;

public class CollaboratorException extends Exception {
    public CollaboratorException(String exceptionMessage){
        super(exceptionMessage);
    }

    public CollaboratorException(String exceptionMessage, Throwable cause){
        super(exceptionMessage, cause);
    }
}
