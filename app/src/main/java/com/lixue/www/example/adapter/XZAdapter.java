package com.lixue.www.example.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.lixue.www.example.R;
import com.lixue.www.example.adapter.util.CommonAdapter;
import com.lixue.www.example.adapter.util.ViewHolder;
import com.lixue.www.example.entity.XZ;
import com.lixue.www.example.ui.MyListView;
import com.lixue.www.example.util.Contant;
import com.lixue.www.example.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename XZAdapter
 * @description -------------------------------------------------------
 * @date 2017/11/9 14:30
 */
public class XZAdapter extends CommonAdapter<XZ> {
    //    private List<String> list;
    private Map<Integer, Map<Integer, Boolean>> map;
    private Map<Integer, Boolean> clickMap;
    private ClickListener clickListener;

    public XZAdapter(Context context, List<XZ> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        map = new HashMap<>();
        clickMap = new HashMap<>();
//        list = new ArrayList<>();
//        list.add("答案1");
//        list.add("答案2");
//        list.add("答案3");
//        list.add("答案4");
    }

    @Override
    public void convert(ViewHolder helper, final XZ item, final int position) {
        helper.setText(R.id.tv_title, (position + 1) + "." + StringUtil.valueOf(item.title));
        MyListView myListView = helper.getView(R.id.mlv_);
        final View view = helper.getView(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (clickMap.containsKey(position) && clickMap.get(position)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        final XXAdapter xxAdapter = new XXAdapter(mContext, item.answers, R.layout.item_xx);
        xxAdapter.setCorrectAnswers(StringUtil.valueOf(item.correctAnswers));
        myListView.setAdapter(xxAdapter);
        if (map.containsKey(position)) {
            for (int i = 0; i < item.answers.size(); i++) {
                if (map.get(position).containsKey(i) && map.get(position).get(i)) {
                    xxAdapter.setSelectItem(i);
                    break;
                }
            }
        }
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position2, long id) {
                Map<Integer, Boolean> map1 = new HashMap<>();
                map1.put(position2, true);
                map.put(position, map1);
                xxAdapter.setSelectItem(position2);
                view.setVisibility(View.VISIBLE);
                clickMap.put(position, true);
                if (item.correctAnswers != null && item.correctAnswers.equals(xxAdapter.getItem(position2))) {
                    Contant.correct++;
                } else {
                    Contant.error++;
                }
                if (clickListener != null) {
                    clickListener.onClick();
                }
            }
        });
    }

    public interface ClickListener {
        void onClick();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
