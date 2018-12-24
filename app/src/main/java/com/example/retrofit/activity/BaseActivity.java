package com.example.retrofit.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.example.retrofit.mvp.presenter.P;
import com.example.retrofit.mvp.presenter.Plistener;
import com.example.retrofit.mvp.ui.Vlistener;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by WZG on 2016/12/26.
 */

public abstract class BaseActivity<T extends BaseApi> extends RxAppCompatActivity implements Vlistener {
    //    加载框可自己定义
    protected ProgressDialog pd;
    T t;
    protected P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = initBaseApi();
        p = new P();
        if(null == pd){
            pd = new ProgressDialog(this);
            pd.setCancelable((null!=t && t.isCancel()));
        }

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
//            presenter = initPresenter();
            initBind();
            initViews(savedInstanceState);
            initData();
            initEvent();
            doBusiness();
        }

        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        p.atach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.detach();
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).destroy();
        }
    }

    protected void showP(){
        if(pd!=null&&!pd.isShowing()){
            pd.show();
        }
    }


    protected void dismissP(){
        if(pd!=null&&pd.isShowing()){
            pd.dismiss();
        }
    }

    public abstract T initBaseApi();

    protected abstract int getLayoutId();

//    public abstract P initPresenter();

    protected void initBind() {
        ButterKnife.bind(this);
    }

    /**
     * 控件初始化
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 事件初始化
     */
    protected abstract void initEvent();

    /**
     * 处理业务逻辑
     */
    protected abstract void doBusiness();

    /**
     * 是否可以使用沉浸式
     *
     * @return the boolean
     */
    protected abstract boolean isImmersionBarEnabled();

    @SuppressLint("ResourceType")
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(Color.WHITE)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).init();
        }
    }
}
