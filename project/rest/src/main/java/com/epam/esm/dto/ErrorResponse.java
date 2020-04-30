package com.epam.esm.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private String errorCode;
    private List<String> message = new ArrayList<>();

    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, List<String> message) {
        this.errorCode = errorCode;
        this.message = new ArrayList<>(message);
    }

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message.add(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object getMessage() {
        return message.size() > 1 ? message : message.get(0);
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(List<String> message) {
        this.message = new ArrayList<>(message);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ErrorResponse response = (ErrorResponse) obj;
        return (errorCode == null ? errorCode == response.errorCode : errorCode.equals(response.errorCode))
                && (message == null ? message == response.message : message.equals(response.message));
    }

    @Override
    public int hashCode() {
        return (errorCode == null ? 0 : errorCode.hashCode()) + (message == null ? 0 : message.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ERROR_CODE=" + errorCode + "; MESSAGE=" + message;
    }
}
