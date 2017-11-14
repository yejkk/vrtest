package com.vrplayer.test.vrplayertest.service;

import android.database.Observable;

import com.vrplayer.test.vrplayertest.util.BaseEntity;
import com.vrplayer.test.vrplayertest.util.UserInfo;
import com.vrplayer.test.vrplayertest.util.VideoUrl;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by userd on 2017/10/24.
 */

public interface  RetrofitService {
    @FormUrlEncoded
    @POST("account/login")
    Observable<BaseEntity<UserInfo>> login(
            @Field("userId") String userId,
            @Field("password") String password
    );

    @GET("video/getUrl")
    Observable<BaseEntity<VideoUrl>> getVideoUrl(
            @Query("id") long id
    );

    @FormUrlEncoded
    @POST("user/addVideo")
    Observable<BaseEntity<Boolean>> addVideo(
            @FieldMap Map<String, Object> map
    );
}
