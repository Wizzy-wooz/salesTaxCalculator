package com.vodzianova.exceptionHandler;

/**
 * Created by elena on 1/25/17.
 */
public class ApiErrorResponse {

    private int code;
    private String message;

    public ApiErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}