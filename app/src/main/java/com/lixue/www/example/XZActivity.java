package com.lixue.www.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lixue.www.example.adapter.XZAdapter;
import com.lixue.www.example.base.BaseActivity;
import com.lixue.www.example.db.AssetsDatabaseManager;
import com.lixue.www.example.db.SqlUtil;
import com.lixue.www.example.entity.XZ;
import com.lixue.www.example.entity.XZBean;
import com.lixue.www.example.util.Contant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XZActivity extends BaseActivity {
    private TextView tv;
    private ListView lv;
    private XZAdapter xzAdapter;
    private List<XZ> list;
    private int size;
    private int correct;
    private int error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xz);
        init();
        getData();
    }

    private void getData() {
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        SQLiteDatabase db1 = mg.getDatabase("forexam");
        // 对数据库进行操作
        Cursor cursor = db1.rawQuery("select a.NAME,b.QUESTION_NAME,c.QUESTION_NAME as RIGHT\n" +
                "from TITLE_NAME_ENTITY a \n" +
                "  left join QUESTION_ENTITY b \n" +
                "  on a._id = b.BELONG_TITLE_ID\n" +
                "  left join QUESTION_ENTITY c \n" +
                "  on a.RIGHT_POSITION = c._id;", new String[]{});
        List<XZBean> xzBeenList = SqlUtil.getSearchXCBean(cursor);
        list = getResult(xzBeenList);
        size = list.size();
        setText();
        xzAdapter.refresh(list);
    }

    private void init() {
        Contant.correct = 0;
        Contant.error = 0;
        tv = getView(R.id.tv_);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        xzAdapter = new XZAdapter(mContext, list, R.layout.item_xz);
        xzAdapter.setClickListener(new XZAdapter.ClickListener() {
            @Override
            public void onClick() {
                setText();
            }
        });
        lv.setAdapter(xzAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    public static List<XZ> getResult(List<XZBean> list) {
        Map<String, List<String>> result = new HashMap<>();
        for (XZBean xzBean : list) {
            if (result.containsKey(xzBean.NAME)) {
                result.get(xzBean.NAME).add(xzBean.QUESTION_NAME);
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(xzBean.QUESTION_NAME);
                result.put(xzBean.NAME, temp);
            }
        }
        List<XZ> list2 = new ArrayList<>();
        XZ xcBean = null;
        if (result != null) {
            for (String key : result.keySet()) {
                xcBean = new XZ();
                xcBean.title = key;
                List<String> answerList = result.get(key);
                xcBean.answers = answerList;
                list2.add(xcBean);
            }
        }

        for (int j = 0; j < list2.size(); j++) {
            for (int k = 0; k < list.size(); k++) {
                if (list.get(k).NAME != null && list.get(k).NAME.equals(list2.get(j).title)) {
                    list2.get(j).correctAnswers = list.get(k).RIGHT;
                    break;
                }
            }
        }

        return list2;
    }

    //Java 遍历map
    public static List<XZ> getXzResult(Map<String, List<String>> map) {
        List<XZ> list = new ArrayList<>();
        XZ xcBean = null;
        if (map != null) {
            for (String key : map.keySet()) {
                xcBean = new XZ();
                xcBean.title = key;
                List<String> answerList = map.get(key);
                xcBean.answers = answerList;
                list.add(xcBean);
            }
        }
        return list;
    }

    private void setText() {
        error = Contant.error;
        correct = Contant.correct;
        tv.setText("总共" + size + "题,回答错误：" + error + ",回答正确：" + correct);
    }



    private void showExitDialog() {
        AlertDialog ad = new AlertDialog.Builder(mContext).setTitle("提示")//设置对话框标题
                .setMessage("是否确定退出本页面？")//设置显示的内容
                .setPositiveButton("确定退出", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        finish();
                    }
                }).setNegativeButton("再看看", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).create();
        ad.setCancelable(true);
        ad.show();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果按下的是返回键，并且没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showExitDialog();
            return false;
        }
        return false;
    }
}
