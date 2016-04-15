package com.snail.iweibo.api;
import com.snail.iweibo.mvp.model.TokenInfo;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 授权
 * Created by alexwan on 16/4/1.
 */
public interface OAuth2ApiService {
    /**
     * @param client_id    申请应用时分配的AppKey。必选
     * @param redirect_uri 授权回调地址，站外应用需与设置的回调地址一致，站内应用需填写canvas page的地址。必选
     * @param scope        申请scope权限所需参数，可一次申请多个scope权限，用逗号分隔
     * @param state        用于保持请求和回调的状态，在回调时，会在Query Parameter中回传该参数。
     *                     开发者可以用这个参数验证请求有效性，也可以记录用户请求授权页前的位置。
     *                     这个参数可用于防止跨站请求伪造（CSRF）攻击
     * @param display      授权页面的终端类型，取值见下面的说明 ：mobile
     * @param forcelogin   是否强制用户重新登录，true：是，false：否。默认false。
     * @param language     授权页语言，缺省为中文简体版，en为英文版。英文版测试中，开发者任何意见可反馈至 @微博API
     * @return
     */
    @GET("oauth2/authorize")
    Observable<String> authorize(@Query("client_id") String client_id, @Query("redirect_uri") String redirect_uri,
                                    @Query("scope") String scope, @Query("state") String state,
                                    @Query("display") String display, @Query("forcelogin") boolean forcelogin,
                                    @Query("language") String language);

    /**
     * 查询用户access_token的授权相关信息，包括授权时间，过期时间和scope权限。
     * @param token 用户授权时生成的access_token。
     * @return TokenInfo
     */
    @POST("oauth2/get_token_info")
    Observable<TokenInfo> getTokenInfo(@Query("access_token") String token);
}

