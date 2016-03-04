package com.snail.iweibo.api;
import com.snail.iweibo.mvp.model.PublicNews;
import com.snail.iweibo.network.RetrofitClient;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.util.Configuration;

import retrofit2.http.Query;
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
     * @param baseApp        是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     */
    public static Observable<PublicNews> getPublicTimeLine(String token, Integer count, Integer page, Integer baseApp) {
        return getWeiboService(Constants.WEIBO_BASE_URL)
            .getPublicTimeLine(token, count, page, baseApp)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * getFriendsTimeline
     * @param token	true	string	采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param sinceId	false	int64	若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param maxId	false	int64	若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count	false	int	单页返回的记录条数，最大不超过100，默认为20。
     * @param page	false	int	返回结果的页码，默认为1。
     * @param baseApp	false	int	是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature	false	int	过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trimUser	false	int	返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    public static Observable<PublicNews> getFriendsTimeline(String token, Long sinceId, Long maxId, Integer count,
                                                            Integer page,Integer baseApp,Integer feature,Integer trimUser) {
        return getWeiboService(Constants.WEIBO_BASE_URL)
                .getFriendsTimeline(token,sinceId,maxId,count,page,baseApp,feature,trimUser)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
