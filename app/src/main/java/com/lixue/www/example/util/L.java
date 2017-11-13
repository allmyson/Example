package com.lixue.www.example.util;

import android.util.Log;

/**
 * Copyright (c) 2015,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：ep-app-branch<br>
 * 摘要：Log工具类<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：李杰<br>
 * 完成日期：2017/6/5<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：李杰<br>
 * 完成日期：2017/6/5<br>
 */

public class L {
    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static String TAG = "lh";

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        L.TAG = TAG;
    }

    public static void setDebugMode(boolean debug) {
        isDebug = debug;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void println(String msg) {
        if (isDebug)
            System.out.println(msg);
    }
}
