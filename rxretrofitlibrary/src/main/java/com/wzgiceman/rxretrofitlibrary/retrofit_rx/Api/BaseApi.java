package com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.ParamsUtil;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.RegexUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public abstract class BaseApi<T> implements Func1<T, String> {
    /*是否能取消加载框*/
    private boolean cancel=false;
    /*是否显示加载框*/
    private boolean showProgress=true;
    /*是否需要缓存处理*/
    private boolean cache=false;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String mothed;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime=60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime=24*60*60*30;
    /*参数转成字符串  参数2个以上,隔开  因为在post请求中很可能遇到请求路径一样  但是在body里面参数不一样的情况*/
    private String paramString;

    public String getParamString() {
        return paramString;
    }

    public void setMap(Map<String, Object> mParams){
        //因为我们接口有个获取随机32位数的方法  所以不能将存这个作为判断缓存的key
        this.map = mParams;
        if(null == mParams){
            paramString = null;
            return;
        }else{
            if(mParams.size()>0){
                StringBuffer stringBuffer = new StringBuffer();
                String[] keys = new String[mParams.size()];
                mParams.keySet().toArray(keys);
                for (int i = 0; i < keys.length; i++) {
                    if (i < keys.length - 1) {
                        stringBuffer.append(keys[i] + "=" + mParams.get(keys[i]) + "&");
                    } else {
                        stringBuffer.append(keys[i] + "=" + mParams.get(keys[i]));
                    }
                }
                paramString = stringBuffer.toString();
            }
        }

    }
    /*提供参数的key value*/
    Map<String,Object> map;
    private boolean show_server_msg_at_last = true;


    public boolean isShow_server_msg_at_last() {
        return show_server_msg_at_last;
    }
    public void setShow_server_msg_at_last(boolean show_server_msg_at_last) {
        this.show_server_msg_at_last = show_server_msg_at_last;
    }
    /*服务器提示消息在自定义消息提示之后*/
    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);



    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public String getMothed() {
        return mothed;
    }

    public void setMothed(String mothed) {
        if(TextUtils.isEmpty(mothed) || mothed.equals("") || mothed.length()<=0){
            throw new HttpTimeException(HttpTimeException.PARAMETER_EMPTY);//方法参数必须设置，不然缓存的地址全是基地址。
        }
        this.mothed = mothed;
    }

    public String getUrl() {
        if(null!=mothed && 0<mothed.length()){
            if(RegexUtil.checkURL(mothed)){
                return mothed;
            }else{
                return RxRetrofitApp.baseUrl+mothed;
            }
        }
        return RxRetrofitApp.baseUrl;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
         return cancel;
     }

     public void setCancel(boolean cancel) {
         this.cancel = cancel;
     }

    @Override
    public String call(T httpResult) {
        BaseResultEntity baseResulte= JSONObject.parseObject(httpResult.toString(),BaseResultEntity.class);
        if (baseResulte.getCode() == 0) {
            throw new HttpTimeException(baseResulte.getMessage());
        }
        return baseResulte.getData();
    }
}
