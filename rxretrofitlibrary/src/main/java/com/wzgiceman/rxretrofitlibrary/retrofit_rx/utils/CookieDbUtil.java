package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DaoMaster;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DaoSession;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.HttpDownManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieResulte;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieResulteDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * 数据缓存
 * 数据库工具类-geendao运用
 * Created by WZG on 2016/10/25.
 */

public class CookieDbUtil {
    private static CookieDbUtil db;
    private final static String dbName = "tests_db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;


    public CookieDbUtil() {
        context= RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName,null);
    }


    /**
     * 获取单例
     * @return
     */
    public static CookieDbUtil getInstance() {
        if (null == db) {
            synchronized (CookieDbUtil.class) {
                if (null == db) {
                    db = new CookieDbUtil();
                }
            }
        }
        return db;
    }

        //原始   但是会报Didn't find class "net.sqlcipher.database.SQLiteOpenHelper”   不影响正常使用  但是涉及到安全类产品  最好改成下面那种  记得导入包compile 'net.zetetic:android-database-sqlcipher:3.5.7@aar'
//    /**
//     * 获取可读数据库
//     */
//    private SQLiteDatabase getReadableDatabase() {
//        if (null == openHelper) {
//            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
//        }
//        SQLiteDatabase db = openHelper.getReadableDatabase();
//        return db;
//    }
//
//    /**
//     * 获取可写数据库
//     */
//    private SQLiteDatabase getWritableDatabase() {
//        if (null == openHelper) {
//            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
//        }
////        SQLiteDatabase db = openHelper.getWritableDatabase();
//
//        return db;
//    }
    /**
     * 获取可读数据库
     */
    private Database getReadableDatabase() {
        if (null == openHelper) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        Database db = openHelper.getEncryptedWritableDb("lxd");
        return db;
    }
    /**
     * 获取可写数据库
     */
    private Database getWritableDatabase() {
        if (null == openHelper) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        Database db = openHelper.getEncryptedWritableDb("lxd");

        return db;
    }


    public void saveCookie(CookieResulte info){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        downInfoDao.insert(info);
    }

    public void updateCookie(CookieResulte info){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        downInfoDao.update(info);
    }

    public void deleteCookie(CookieResulte info){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        downInfoDao.delete(info);
    }


    public CookieResulte queryCookieBy(String  url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = downInfoDao.queryBuilder();
        qb.where(CookieResulteDao.Properties.Url.eq(url));
        List<CookieResulte> list = qb.list();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    public List<CookieResulte> queryCookieAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        QueryBuilder<CookieResulte> qb = downInfoDao.queryBuilder();
        return qb.list();
    }
}
