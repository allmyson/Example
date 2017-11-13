package com.lixue.www.example.base;

import android.app.Application;

import com.lixue.www.example.db.AssetsDatabaseManager;

/**
 * @author lh
 * @version 1.0.0
 * @filename App
 * @description -------------------------------------------------------
 * @date 2017/11/9 13:04
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AssetsDatabaseManager.initManager(this);
    }
}
