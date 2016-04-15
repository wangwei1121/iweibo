package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * TokenInfo
 * Created by alexwan on 16/4/15.
 */
public class TokenInfo implements Serializable {
    private String uid; // 授权用户的uid。
    private String appkey; // access_token所属的应用appkey。
    private String scope; // 用户授权的scope权限。
    private String create_at; // access_token的创建时间，从1970年到创建时间的秒数。
    private String expire_in; // access_token的剩余时间，单位是秒数。

    public String getUid() {
        return uid;
    }

    public String getAppkey() {
        return appkey;
    }

    public String getScope() {
        return scope;
    }

    public String getCreateAt() {
        return create_at;
    }

    public String getExpireIn() {
        return expire_in;
    }
}
