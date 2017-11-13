package com.lixue.www.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixue.www.example.R;
import com.lixue.www.example.adapter.util.CommonAdapter;
import com.lixue.www.example.adapter.util.ViewHolder;
import com.lixue.www.example.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename XXAdapter
 * @description -------------------------------------------------------
 * @date 2017/11/9 14:31
 */
public class XXAdapter extends CommonAdapter<String> {
    private String correctAnswers;
    public XXAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_xx, StringUtil.valueOf(item));
        TextView tv = helper.getView(R.id.tv_xx);
        if(selectItem!=-1) {
            if (correctAnswers.equals(StringUtil.valueOf(item))) {
                helper.setImageResource(R.id.iv_result, R.mipmap.ic_correct);
                tv.setTextColor(Color.parseColor("#d81e06"));
            } else {
                helper.setImageResource(R.id.iv_result, R.mipmap.ic_error);
                tv.setTextColor(Color.parseColor("#8a8a8a"));
            }
        }
        ImageView iv = helper.getView(R.id.iv_result);
        if (selectItem == position) {
            iv.setVisibility(View.VISIBLE);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
