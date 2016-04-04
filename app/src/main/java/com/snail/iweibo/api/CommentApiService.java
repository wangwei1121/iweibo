package com.snail.iweibo.api;
import com.snail.iweibo.mvp.model.CommentList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 评论
 * Created by alexwan on 16/4/1.
 */
public interface CommentApiService{

    /**
     *
     * @param token 必选 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param id 必选 需要查询的微博ID。
     * @param sinceId 若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param maxId 若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
     * @return Observable<CommentList>
     */
    @GET("2/comments/show.json")
    Observable<CommentList> getComments(@Query("access_token") String token ,
                                        @Query("id") long id ,
                                        @Query("since_id") long sinceId,
                                        @Query("max_id") long maxId,
                                        @Query("count") int count,
                                        @Query("page") int page,
                                        @Query("filter_by_author") int filter_by_author);
}
