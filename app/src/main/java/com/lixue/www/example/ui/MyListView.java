package com.lixue.www.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author lh
 * @version 1.0.0
 * @filename MyListView
 * @description -------------------------------------------------------
 * @date 2017/3/7 19:25
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec
                , MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
