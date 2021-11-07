package com.stefan.adassignmentbe.model;

public class ErrorMessage {

    private String errorMessage;

    public static ErrorMessage of(String errorMessage) {
        ErrorMessage result = new ErrorMessage();
        result.setErrorMessage(errorMessage);
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
