package com.wzgiceman.rxretrofitlibrary.retrofit_rx;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2017/6/29.
 */

public class AppMgr {
    // Activity栈
    private Stack<Activity> activityStack;
    // 单例模式
    private static AppMgr instance;

    public AppMgr() {
    }

    /**
     * 单一实例
     */
    public static AppMgr getInstance() {
        if (null == instance) {
            instance = new AppMgr();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity(){
        if(null == activityStack || activityStack.empty()){
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (null == activityStack || activityStack.empty()) {
            return;
        }
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if(null == activityStack || activityStack.empty()){
                return ;
            }
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if(null == activityStack || activityStack.empty()){
            return ;
        }
        List<Activity> list = new ArrayList<>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                list.add(activity);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            finishActivity(list.get(i));
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if(null == activityStack || activityStack.empty()){
            return ;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
//    /**
//     * 退出应用程序  重启？未验证
//     */
//    @SuppressWarnings("deprecation")
//    public void AppExit(Context context) {
//        try {
//            finishAllActivity();
//            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.restartPackage(context.getPackageName());
//            System.exit(0);
//        } catch (Exception e) {
//        }
//    }

    /**
     * 考虑到第三方集成的Activity  所以这里将所有的BaseActivity.this 更换成 AppMgr.getInstance().currentActivity()
     */
    public void quit(){
//        Intent it = new Intent(AppMgr.getInstance().currentActivity(),LoginActivity.class);
//        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        AppMgr.getInstance().currentActivity().startActivity(it);
////        AppMgr.getInstance().currentActivity().finish();
//        AppMgr.getInstance().finishAllActivity();
    }
}
