package com.wzgiceman.rxretrofitlibrary.retrofit_rx;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.wzgiceman.rxretrofitlibrary.BuildConfig;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.HttpsUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.SSLSocketClient;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 全局app
 * Created by WZG on 2016/12/12.
 */

public class RxRetrofitApp  {
    public static final String TAG ="lxd";
    private static Application application;

    public static void init(Application app){
        setApplication(app);
        /*权限以及 SharedPreferences初始化*/
        Utils.init(app);
        retrofit = getRetrofitInstance();

    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e(TAG, activity.getLocalClassName() + "  onActivityCreated");
                AppMgr.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e(TAG, activity.getLocalClassName() + "  onActivityDestroyed");
                AppMgr.getInstance().finishActivity(activity);
            }
        });
    }

    /*网络请求的初始化*/
    static Retrofit retrofit;
    public static final long connect_time_out = 6;//默认6秒超时
    public static final String baseUrl = "https://api.bishen.org/";
    public static Retrofit getRetrofitInstance() {
        if (null == retrofit) {
            //手动创建一个OkHttpClient并设置超时时间缓存等设置
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                if (BuildConfig.DEBUG) {
                    builder.addInterceptor(new LogInterceptor());
                }
            }
            builder.connectTimeout(connect_time_out, TimeUnit.SECONDS);
            /*---------------ssl证书验证等方式 start-------------------*/
//            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(RxRetrofitApp.getApplication().getAssets().open("server.cer"));//记得try catch
//            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
////        //配置https的域名匹配规则，使用不当会导致https握手失败
//            builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
            /*---------------ssl证书验证等方式 end---------------------*/

            /*---------------忽略ssl证书验证方式 start-------------------*/
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
            builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
            /*---------------忽略ssl证书验证方式 end---------------------*/
            /*创建retrofit对象*/
            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }
}
