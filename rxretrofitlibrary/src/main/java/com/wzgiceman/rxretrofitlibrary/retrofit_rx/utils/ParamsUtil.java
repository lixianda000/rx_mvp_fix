package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * 跟服务器通信协议用的
 */
public class ParamsUtil {
    private static String KEY = "key=bishensign2018!@#$";

    public static JSONObject cmd_sign(@NonNull Map<String, Object> has, Context context) {
        //有些请求没有参数  但是也要这四个固定的参数作为校验  所以这里注释
//        if(has.isEmpty())
//        {
//            return null;
//        }
        has.put("timestamp", ParamsUtil.getTimeString());
        has.put("did", ParamsUtil.getAppID(context));
        has.put("noncestr", ParamsUtil.getRandomString(32));
        String[] stringkeys = new String[has.size()];
        has.keySet().toArray(stringkeys);
        for (String s : stringkeys) {
            System.out.print("hashmap的key转成string[]：" + s + "  ，");
        }
        Arrays.sort(stringkeys);
        for (String s : stringkeys) {
            System.out.print("string[]排序后：" + s + "  ，");
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : stringkeys) {
            stringBuffer.append(s + "=" + has.get(s) + "&");
        }
        stringBuffer.append(KEY);//拼接上固定的一个标记

        Log.d("JACQUES", stringBuffer.toString());

        String sign = MD5Utils.md5(stringBuffer.toString()).toUpperCase();
        System.out.print("签名：" + sign);
        stringBuffer.setLength(0);//清空资源
        has.put("sign", sign);
        String re = new Gson().toJson(has);
        JSONObject object;
        try {
            object = new JSONObject(re);
        } catch (JSONException e) {
            throw new RuntimeException("拼接请求参数json错误");
        }

        return object;
    }

    //get请求方式   将这四个固定的参数作为校验  拼接在url后边用
    public static String cmd_sign2string(@NonNull Map<String, Object> has, Context context) {
        //有些请求没有参数  但是也要这四个固定的参数作为校验  所以这里注释
//        if(has.isEmpty())
//        {
//            return null;
//        }
        has.put("timestamp", ParamsUtil.getTimeString());
        has.put("did", ParamsUtil.getAppID(context));
        has.put("noncestr", ParamsUtil.getRandomString(32));
        String[] stringkeys = new String[has.size()];
        has.keySet().toArray(stringkeys);
        for (String s : stringkeys) {
            System.out.print("hashmap的key转成string[]：" + s + "  ，");
        }
        Arrays.sort(stringkeys);
        for (String s : stringkeys) {
            System.out.print("string[]排序后：" + s + "  ，");
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : stringkeys) {
            stringBuffer.append(s + "=" + has.get(s) + "&");
        }
        stringBuffer.append(KEY);//拼接上固定的一个标记
        String sign = MD5Utils.md5(stringBuffer.toString()).toUpperCase();
        System.out.print("签名：" + sign);
        stringBuffer.setLength(0);//清空资源
        has.put("sign", sign);

        String[] keys = new String[has.size()];
        has.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            if (i < keys.length - 1) {
                stringBuffer.append(keys[i] + "=" + has.get(keys[i]) + "&");
            } else {
                stringBuffer.append(keys[i] + "=" + has.get(keys[i]));
            }
        }

        return stringBuffer.toString();
    }

    /**
     * 生成随机长度字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @param context
     * @return 构建手机唯一标志
     */
    public static String getAppID(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        return id;
    }

    /**
     * @return 时间的
     */
    public static String getTimeString() {
        return "" + System.currentTimeMillis();
    }
}
