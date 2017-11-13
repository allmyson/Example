package com.lixue.www.example.util;

/**
 * @author lh
 * @version 1.0.0
 * @filename StringUtil
 * @description -------------------------------------------------------
 * @date 2017/11/9 14:39
 */
public class StringUtil {
    public static String valueOf(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }


    public static boolean isCanUse() {
        return true;
//        //2017-11-13 18:00:16
//        if (System.currentTimeMillis() > 1510567216000L) {
//            return false;
//        } else {
//            return true;
//        }
    }

    public static boolean isBlank(String trim) {
        if (trim == null || "".equals(trim)) {
            return true;
        }
        return false;
    }
}
