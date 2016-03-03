package com.snail.iweibo.api;
import com.snail.iweibo.mvp.model.PublicNews;
import com.snail.iweibo.network.RetrofitClient;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.util.Configuration;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ApiService
 * Created by alexwan on 16/1/30.
 */
public class ApiServiceHelper {


    /**
     * 微博API接口
     *
     * @return WeiboApiService
     */
    private static WeiboApiService getWeiboService(String baseUrl) {
        return RetrofitClient.instance(baseUrl).create(WeiboApiService.class);
    }


    /**
     * getPublicTimeLine
     *
     * @param token      必选：采用OAuth授权方式为必填参数OAuth授权后获得。
     * @param count      单页返回的记录条数，默认为50。
     * @param page       返回结果的页码，默认为1。
     * @param app        是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     */
    public static Observable<PublicNews> getPublicTimeLine(String token, int count, int page, int app) {
        return getWeiboService(Constants.WEIBO_BASE_URL)
            .getPublicTimeLine(token, count, page, app)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    }


}
