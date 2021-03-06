package com.snail.iweibo.api;

import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.StatusList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 微博
 * Created by alexwan on 16/1/30.
 */
public interface WeiBoApiService {
    /**
     * @param token 必选：采用OAuth授权方式为必填参数OAuth授权后获得。
     * @param count 单页返回的记录条数，默认为50。
     * @param page  返回结果的页码，默认为1。
     * @param app   是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @return Statuses
     */
    @GET("2/statuses/public_timeline.json")
    Observable<StatusList> getPublicTimeLine(@Query("access_token") String token, @Query("count") int count, @Query
        ("page") int page, @Query("base_app") int app);

    /**
     * @param token     采用OAuth授权方式为必填参数，OAuth授权后获得。 必选
     * @param since_id  若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id    若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count     单页返回的记录条数，最大不超过100，默认为20。
     * @param page      返回结果的页码，默认为1。
     * @param app       是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature   过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trim_user 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     * @return Statuses
     */
    @GET("2/statuses/friends_timeline.json")
    Observable<StatusList> getFriendsTimeLine(@Query("access_token") String token, @Query("since_id") long since_id,
                                              @Query("max_id") long max_id, @Query("count") int count,
                                              @Query("page") int page, @Query("base_app") int app,
                                              @Query("feature") int feature, @Query("trim_user") int trim_user);

    /**
     * 获取某个用户最新发表的微博列表
     * 获取自己的微博，参数uid与screen_name可以不填，则自动获取当前登录用户的微博；
     * 指定获取他人的微博，参数uid与screen_name二者必选其一，且只能选其一；
     *
     * @param token       采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param screen_name 需要查询的用户昵称。
     * @param since_id    若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
     * @param max_id      若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
     * @param count       单页返回的记录条数，最大不超过100，超过100以100处理，默认为20。
     * @param page        返回结果的页码，默认为1。
     * @param app         是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature     过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @param trim_user   返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
     * @return StatusList
     */
    @GET("2/statuses/user_timeline.json")
    Observable<StatusList> getUserTimeLine(@Query("access_token") String token,
                                           @Query("screen_name") String screen_name,
                                           @Query("since_id") long since_id,
                                           @Query("max_id") long max_id,
                                           @Query("count") int count,
                                           @Query("page") int page,
                                           @Query("base_app") int app,
                                           @Query("feature") int feature,
                                           @Query("trim_user") int trim_user);


    /**
     * 转发一条微博
     * @param token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param id 要转发的微博ID。
     * @param repostTxt 添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”。
     * @param isComment 是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0 。
     * @param ipAddress 开发者上报的操作用户真实IP，形如：211.156.0.1。
     * @return Status
     */
    @FormUrlEncoded
    @POST("2/statuses/repost.json")
    Observable<Status> repostStatus(@Field("access_token") String token , @Field("id") long id ,
                                    @Field(value= "status" ,encoded = false) String repostTxt ,
                                    @Field("is_comment") int isComment ,
                                    @Field("rip") String ipAddress);
}
