package com.lixue.www.example.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.lixue.www.example.entity.TK;
import com.lixue.www.example.entity.XZBean;
import com.lixue.www.example.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：SqlUtil<br>
 * 摘要：数据库sql操作工具<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：彭粟<br>
 * 完成日期：2016/11/3<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：彭粟<br>
 * 完成日期：2016/11/3<br>
 */

public class SqlUtil {
    /**
     * @return void
     * @author 彭粟
     * @version 1.0
     * @Description: 新增  修改  删除 操作  true为执行正常  false 为执行失败
     * @time： 2016/11/3
     */
    public static boolean insertOrUpdateOrDeleteSql(String sql, SQLiteOpenHelper dbOpenHelper) {
        boolean result = true;
        try {
            dbOpenHelper.getWritableDatabase().execSQL(sql);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return void
     * @author 彭粟
     * @version 1.0
     * @Description: 新增  修改  删除 操作  true为执行正常  false 为执行失败  args是参数值数组
     * @time： 2016/11/3
     */
    public static boolean insertOrUpdateOrDeleteSql(String sql, String[] args, SQLiteOpenHelper dbOpenHelper) {
        boolean result = true;
        try {
            dbOpenHelper.getWritableDatabase().execSQL(sql, args);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return void
     * @author 彭粟
     * @version 1.0
     * @Description: 创建表的方法 rue为执行正常  false 为执行失败
     * @time： 2016/11/3
     */

    public static boolean createTable(String sql, SQLiteOpenHelper dbOpenHelper) {
        boolean result = true;
        try {
            dbOpenHelper.getWritableDatabase().execSQL(sql);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public static List<Map<String, Object>> getSearchResult(Cursor cursor) {
        //获取表的所有列名
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        try {
            if (cursor != null) {
                String[] columnNames = cursor.getColumnNames();
                while (cursor.moveToNext()) {
                    map = new HashMap<>();
                    for (String str : columnNames) {
                        map.put(str, cursor.getString(cursor.getColumnIndex(str)));
                    }
                    resultList.add(map);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static String getSearchJson(Cursor cursor) {
        //获取表的所有列名
        List<Map<String, Object>> resultList = getSearchResult(cursor);
        String result = new Gson().toJson(resultList);
        return result;
    }

    public static List<XZBean> getSearchXCBean(Cursor cursor) {
        List<Map<String, Object>> resultList = getSearchResult(cursor);
        List<XZBean> list = new ArrayList<>();
        XZBean xzBean = null;
        for (int i = 0; i < resultList.size(); i++) {
            xzBean = new XZBean();
            Map<String, Object> map = resultList.get(i);
            xzBean.NAME = StringUtil.valueOf(map.get("NAME"));
            xzBean.QUESTION_NAME = StringUtil.valueOf(map.get("QUESTION_NAME"));
            xzBean.RIGHT = StringUtil.valueOf(map.get("RIGHT"));
            list.add(xzBean);
        }
        return list;
    }

    /**
     * 得到数据库下所有的表名
     *
     * @return
     */
    public static List<String> getTableNames(SQLiteDatabase db) {
        List<String> list = new ArrayList<String>();
        String name = null;
        if (db != null) {
            Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
            while (cursor.moveToNext()) {
                //遍历出表名
                name = cursor.getString(0);
                list.add(name);
            }
            if (list.contains("android_metadata")) {
                list.remove("android_metadata");
            }
            //删除数据的时候不删除用户行为分析表
            /**2016-06-02 by lh**/
            if (list.contains("statistics")) {
                list.remove("statistics");
            }
        }
        return list;
    }

    /**
     * 删除所有表的数据
     *
     * @return
     */
    public static boolean deleteAllTable(Context context, SQLiteDatabase db) {
        List<String> list = getTableNames(db);
        if (list != null && list.size() > 0) {
            for (String s : list) {
                db.execSQL("delete from " + s);
            }
        }
        return true;
    }




    public static List<TK> getTK(Cursor cursor) {
        List<Map<String, Object>> resultList = getSearchResult(cursor);
        List<TK> list = new ArrayList<>();
        TK tk = null;
        for (int i = 0; i < resultList.size(); i++) {
            tk = new TK();
            Map<String, Object> map = resultList.get(i);
            tk.zj = String.valueOf(map.get("BELONG_SECTION_ID"));
            tk.titleName = StringUtil.valueOf(map.get("TITLE_NAME"));
            tk.answer = StringUtil.valueOf(map.get("ANSWER"));
            list.add(tk);
        }
        return list;
    }
}
