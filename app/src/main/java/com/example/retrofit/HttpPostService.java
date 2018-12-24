package com.example.retrofit;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 测试接口service-post相关
 * Created by WZG on 2016/12/19.
 */

public interface HttpPostService {

//    @FormUrlEncoded
//    @POST("AppFiftyToneGraph/videoLink")
//    Observable<String> getAllVedioBy(@Field("once_no") boolean once_no);

    @Headers({"User-Agent:android", "Content-Type: application/json", "Accept: application/json"})
    @GET//不要用这种 @GET("/api/v1/user/get_welfare")  直接设置到BaseApi的setMothed里面  方便缓存  不然  缓存的key一直是基地址
    Observable<String> getAllVedioBy(@Url String url);

}
