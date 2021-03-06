package com.lixue.www.example.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;

import com.lixue.www.example.fragment.PLFragment;

import java.util.Calendar;

/**
 * @author lh
 * @version 1.0.0
 * @filename DialogUtil
 * @description -------------------------------------------------------
 * @date 2017/11/11 16:01
 */
public class DialogUtil {
    /**
     * dialog tag
     */
    private static String mDialogTag = "dialog";


    /**
     * 描述：移除Fragment.
     *
     * @param context the context
     */
    public static void removeDialog(final Context context) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    FragmentActivity activity = (FragmentActivity) context;
                    FragmentTransaction ft = activity
                            .getSupportFragmentManager().beginTransaction();
                    // 指定一个系统转场动画
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    Fragment prev = activity.getSupportFragmentManager()
                            .findFragmentByTag(mDialogTag);
                    if (prev != null) {
                        ft.remove(prev);
                    }
//					ft.addToBackStack(null);
                    ft.commit();
                } catch (Exception e) {
                    // 可能有Activity已经被销毁的异常
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从父亲布局中移除自己
     *
     * @param v
     */
    public static void removeSelfFromParent(View v) {
        ViewParent parent = v.getParent();
        if (parent != null) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(v);
            }
        }
    }


    /**
     * 显示日期选择框
     *
     * @param context
     * @param listener
     */
    public static void showDateDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, monthOfYear, dayOfMonth);
        datePickerDialog.show();
    }

    /**
     * 显示时分
     *
     * @param context
     * @param listener
     */
    public static void showDateDialogSF(Context context, TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int dayOfMonth = calendar.get(Calendar.MONTH) + 1;

        TimePickerDialog datePickerDialog = new TimePickerDialog(context, listener, hour, dayOfMonth, true);
        datePickerDialog.show();
    }

    /**
     * 弹出评论输入框
     * @param context
     * @param listener
     * @return
     */
    public static PLFragment showPL(Context context, PLFragment.ResultListener listener){
        FragmentActivity activity = (FragmentActivity) context;
        removeDialog(activity);
        PLFragment newFragment = PLFragment.newInstance(DialogFragment.STYLE_NO_TITLE, android.R.style
                .Theme_Holo_Light_Dialog);
        newFragment.setListener(listener);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        // 指定一个过渡动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.show(ft, mDialogTag);
        return newFragment;
    }

    //年月日对话框
    public static void showYMDDialog(Context context) {
        DialogUtil.showDateDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthStr = (month + 1) > 9 ? String.valueOf((month + 1)) : "0" + String.valueOf((month + 1));
                String dayStr = dayOfMonth > 9 ? String.valueOf(dayOfMonth) : "0" + String.valueOf(dayOfMonth);
                String result = year+"-"+monthStr+"-"+dayStr;
                L.e(result);
            }
        });
    }

}
