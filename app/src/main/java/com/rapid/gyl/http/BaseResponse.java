package com.rapid.gyl.http;

import com.rapid.gyl.app.Constants;

import java.io.Serializable;

/**
 * The server returns the basic format of the data
 */
public class BaseResponse<T> implements Serializable {
    public String errorCode;
    public String message;
    public String time;
    public T data;

    public boolean success() {
        return Constants.REQUEST_SUCCESS.equals(errorCode);
    }

    public String getCode() {
        return errorCode;
    }

    public void setCode(String code) {
        this.errorCode = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", data=" + data +
                '}';
    }
}
