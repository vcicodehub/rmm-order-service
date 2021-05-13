package com.signet.exceptions;

public class SignetIllegalArgumentException extends SignetServiceException {

    public SignetIllegalArgumentException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public SignetIllegalArgumentException(String errorMessage) {
        super(errorMessage);
    }
    
}
