package com.signet.exceptions;

public abstract class SignetServiceException extends Exception {
    public SignetServiceException(String errorMessage, Throwable err) {
      super(errorMessage, err);
    }
    public SignetServiceException(String errorMessage) {
      super(errorMessage);
    }
  }
