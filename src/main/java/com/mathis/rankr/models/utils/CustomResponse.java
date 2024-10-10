package com.mathis.rankr.models.utils;

import org.springframework.http.HttpStatus;

public class CustomResponse {
    private int status;
    private String error;
    private String message;


    public CustomResponse(HttpStatus status, String message) {
        this.message = message;
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
