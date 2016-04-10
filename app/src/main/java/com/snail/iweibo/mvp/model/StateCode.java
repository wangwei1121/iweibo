package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * StateCode
 * Created by alexwan on 16/4/10.
 */
public class StateCode implements Serializable {
    private String code; // 用于调用oauth2/access_token接口，获取授权后的access token。
    private String state; // 如果传递参数，会回传该参数。

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
