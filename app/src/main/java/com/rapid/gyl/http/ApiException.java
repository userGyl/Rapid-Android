package com.rapid.gyl.http;

import com.rapid.gyl.app.Constants;

/**
 * Exception handling at request time
 */

public class ApiException extends RuntimeException {

    private String message;
    private int code = Constants.REQUEST_DEFAULT_ERR;
    private String time;

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ApiException(String message, int code, String time) {
        this.message = message;
        this.code = code;
        this.time = time;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
