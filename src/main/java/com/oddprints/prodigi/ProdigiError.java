package com.oddprints.prodigi;


@SuppressWarnings("serial")
public class ProdigiError extends RuntimeException {
    private String errorMessage;
    private int code;

    public ProdigiError() {
    }

    public ProdigiError(String errorMessage, int code) {
        this.errorMessage = errorMessage;
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return code + (errorMessage == null ? "" : ": " + errorMessage);
    }
}
