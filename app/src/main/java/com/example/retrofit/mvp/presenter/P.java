package com.example.retrofit.mvp.presenter;

import com.example.retrofit.mvp.model.M;
import com.example.retrofit.mvp.model.Mlistener;
import com.example.retrofit.mvp.ui.Vlistener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;

/**
 * presenter两个接口，一个请求一个回调
 * 目的：确保Model层不直接操作View层
 * Created by WZG on 2016/12/26.
 */

public class P implements Plistener,PVlistener{

    private Vlistener vlistener;

    private Mlistener mlistener;


    public P() {
        mlistener =new M(this);
    }

    public void atach(Vlistener vlistener){
        this.vlistener = vlistener;
    }
    public void detach(){
        this.vlistener = null;
    }

    @Override
    public void startPost(RxAppCompatActivity rxAppCompatActivity, BaseApi baseApi) {
        if(null == baseApi){
            onError(new ApiException(new Throwable("请求的相关参数BaseApi,  一定要写!!!")));
        }
        if(null!=vlistener && baseApi.isShowProgress()){
            vlistener.showProg();
        }
        mlistener.startPost(rxAppCompatActivity,baseApi);
    }

    @Override
    public void doTest(String msg) {
        mlistener.testDo(msg);
    }


    @Override
    public void testPSuc(String msg) {
        vlistener.onTestNext(msg);
    }


    @Override
    public void onNext(String resulte, String mothead) {
        vlistener.onNext(resulte,mothead);
        vlistener.dismissProg();
    }


    @Override
    public void onError(ApiException e) {
        vlistener.onError(e);
        vlistener.dismissProg();
    }
}
