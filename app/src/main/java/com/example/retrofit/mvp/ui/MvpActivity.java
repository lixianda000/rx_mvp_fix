package com.example.retrofit.mvp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.example.retrofit.R;
import com.example.retrofit.activity.BaseActivity;
import com.example.retrofit.entity.UserInfo;
import com.example.retrofit.entity.api.SubjectPostApi;
import com.example.retrofit.entity.resulte.RetrofitEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import butterknife.BindView;
import butterknife.OnClick;

public class MvpActivity extends BaseActivity<SubjectPostApi> implements Vlistener {
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    SubjectPostApi subjectPostApi;

    @Override
    public SubjectPostApi initBaseApi() {
        subjectPostApi = new SubjectPostApi();
        return subjectPostApi;
    }
//
//    @Override
//    public void DoNetworkRequests() {
//
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void doBusiness() {
        p.startPost(MvpActivity.this,initBaseApi());
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @OnClick(value = R.id.tv_test)
    void onTvTestClick(View view) {
        p.doTest(""+subjectPostApi.getMothed());
    }


    @OnClick(value = R.id.tv_msg)
    void onTvMsgClick(View view) {
        p.startPost(MvpActivity.this, subjectPostApi);
    }

    @Override
    public void showProg() {
        showP();
    }

    @Override
    public void dismissProg() {
        Log.e("tag","--->dismiss");
        dismissP();
    }

    @Override
    public void onNext(String s, String m) {
        tvMsg.setText("返回数据:" + s);
        JSONObject.parseObject(s,RetrofitEntity.class);
        BaseResultEntity<UserInfo> baseResultEntity = JSONObject.parseObject(s,BaseResultEntity.class);
    }

    @Override
    public void onError(ApiException e) {
        tvMsg.setText("错误信息:" + e.getMessage() + "------" + e.getCode());
    }

    @Override
    public void onTestNext(String msg) {
        tvTest.setText("测试返回数据了：" + msg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProg();
    }
}
