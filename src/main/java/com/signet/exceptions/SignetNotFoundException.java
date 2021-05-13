package com.signet.exceptions;

public class SignetNotFoundException extends SignetServiceException {

    public SignetNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public SignetNotFoundException(String errorMessage) {
        super(errorMessage);
    }
    
}
