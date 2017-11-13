package com.lixue.www.example.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixue.www.example.R;
import com.lixue.www.example.adapter.util.CommonAdapter;
import com.lixue.www.example.adapter.util.ViewHolder;
import com.lixue.www.example.entity.TK;
import com.lixue.www.example.fragment.PLFragment;
import com.lixue.www.example.util.Contant;
import com.lixue.www.example.util.DialogUtil;
import com.lixue.www.example.util.L;
import com.lixue.www.example.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename TKAdapter
 * @description -------------------------------------------------------
 * @date 2017/11/10 13:44
 */
public class TKAdapter extends CommonAdapter<TK> {

    private List<String> etList;
    private List<Boolean> booleenList;

    public TKAdapter(Context context, List<TK> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        etList = new ArrayList<>();
        booleenList = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            etList.add("");
            booleenList.add(false);
        }
    }

    @Override
    public void convert(ViewHolder helper, final TK item, final int position) {
        helper.setText(R.id.tv_title, (position + 1) + "." + StringUtil.valueOf(item.titleName));
        final TextView tv = helper.getView(R.id.tv_answer);
        final ImageView iv = helper.getView(R.id.iv_result);
        final TextView rightTV = helper.getView(R.id.tv_right);
        helper.setText(R.id.tv_right, StringUtil.valueOf(item.answer));
        tv.setText(StringUtil.valueOf(etList.get(position)));
        if (StringUtil.isBlank(etList.get(position))) {
            //没做过
            iv.setVisibility(View.GONE);
            rightTV.setVisibility(View.GONE);
        } else {
            //做过
            if (booleenList.get(position)) {
                //做正确
                iv.setImageResource(R.mipmap.ic_correct);
                rightTV.setVisibility(View.GONE);
            } else {
                //做错误
                iv.setImageResource(R.mipmap.ic_error);
                rightTV.setVisibility(View.VISIBLE);
            }
            iv.setVisibility(View.VISIBLE);
        }


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isBlank(etList.get(position))) {
                    DialogUtil.showPL(mContext, new PLFragment.ResultListener() {
                        @Override
                        public void result(String text) {
                            DialogUtil.removeDialog(mContext);
                            L.e("text=" + text);
                            tv.setText(StringUtil.valueOf(text));
                            etList.set(position, StringUtil.valueOf(text));
                            if (StringUtil.valueOf(text).equals(item.answer)) {
                                //回答正确
                                iv.setImageResource(R.mipmap.ic_correct);
                                rightTV.setVisibility(View.GONE);
                                booleenList.set(position, true);
                                Contant.tkcorrect++;
                            } else {
                                //回答错误
                                iv.setImageResource(R.mipmap.ic_error);
                                rightTV.setVisibility(View.VISIBLE);
                                booleenList.set(position, false);
                                Contant.tkerror++;
                            }
                            iv.setVisibility(View.VISIBLE);
                            if (clickListener != null) {
                                clickListener.onClick();
                            }
                        }
                    });
                }
            }
        });
    }

    private ClickListener clickListener;

    public void setTKClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick();
    }

    @Override
    public void refresh(List<TK> mDatas) {
        etList.clear();
        booleenList.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            etList.add("");
            booleenList.add(false);
        }
        super.refresh(mDatas);
    }
}
