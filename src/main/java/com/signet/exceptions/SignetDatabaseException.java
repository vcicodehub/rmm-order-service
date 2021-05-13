package com.signet.exceptions;

public class SignetDatabaseException extends SignetServiceException {

    public SignetDatabaseException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public SignetDatabaseException(String errorMessage) {
        super(errorMessage);
    }
    
}
