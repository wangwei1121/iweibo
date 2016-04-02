package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * 错误信息结构体。
 * Created by alexwan on 16/4/1.
 */
public class ErrorInfo implements Serializable {
    private String error;
    private String error_code;
    private String request;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
