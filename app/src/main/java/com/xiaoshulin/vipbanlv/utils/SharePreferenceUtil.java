package com.xiaoshulin.vipbanlv.utils;

import android.content.SharedPreferences;

import com.xiaoshulin.vipbanlv.App;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jipeng on 2018/5/19.
 */

public class SharePreferenceUtil {
    private static SharePreferenceUtil instance;

    public static SharePreferenceUtil getinstance() {
        instance = new SharePreferenceUtil();
        return instance;
    }

    public void setStringUID(String uid) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        preferences.edit().putString("uid", uid).commit();
    }

    public String getStringUId() {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        String uid = preferences.getString("uid", "");
        return uid;
    }

    public void setStringUIdToken(String uid) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        preferences.edit().putString("uidtoken", uid).commit();
    }

    public String getStringUIdToken() {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        String uid = preferences.getString("uidtoken", "");
        return uid;
    }



    public void setString(String key,String uid) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        preferences.edit().putString(key, uid).commit();
    }

    public String getString(String key) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        String uid = preferences.getString(key, "");
        return uid;
    }


    public boolean getIsFrist()
    {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        boolean isfrist = preferences.getBoolean("isfrist", false);
        return isfrist;
    }

    public void setIsFrist(boolean frist) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        preferences.edit().putBoolean("isfrist",frist).commit();
    }

    public String getMyIcon()
    {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        String isfrist = preferences.getString("myicon","");
        return isfrist;
    }

    public void setMyIcon(String frist) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        preferences.edit().putString("myicon",frist).commit();
    }


    public String getVipIcon()
    {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        String isfrist = preferences.getString("vipjson","");
        return isfrist;
    }

    public void setVipIcon(String json) {
        SharedPreferences preferences = App.app.getSharedPreferences("data", MODE_PRIVATE);
        preferences.edit().putString("vipjson",json).commit();
    }


}
