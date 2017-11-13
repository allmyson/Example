package com.lixue.www.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.lixue.www.example.adapter.TKAdapter;
import com.lixue.www.example.base.BaseActivity;
import com.lixue.www.example.db.AssetsDatabaseManager;
import com.lixue.www.example.db.SqlUtil;
import com.lixue.www.example.entity.TK;
import com.lixue.www.example.util.Contant;

import java.util.ArrayList;
import java.util.List;


public class TKActivity extends BaseActivity {
    private TextView tv;
    private ListView lv;
    private TKAdapter tkAdapter;
    private List<TK> list;
    private int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tk);
        init();
        getData();
    }

    private void getData() {
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        SQLiteDatabase db1 = mg.getDatabase("forexam");
        // 对数据库进行操作
        Cursor cursor = db1.rawQuery("select * from PROJECT_ENTITY", new String[]{});
        list = SqlUtil.getTK(cursor);
        size = list.size();
        setText();
        tkAdapter.refresh(list);

    }

    private void init() {
        Contant.tkcorrect = 0;
        Contant.tkerror = 0;
        tv = getView(R.id.tv_);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        tkAdapter = new TKAdapter(mContext,list,R.layout.item_tk);
        tkAdapter.setTKClickListener(new TKAdapter.ClickListener() {
            @Override
            public void onClick() {
                setText();
            }
        });
        lv.setAdapter(tkAdapter);
    }


    private void setText() {
        tv.setText("总共" + size + "题,回答错误：" + Contant.tkerror + ",回答正确：" + Contant.tkcorrect);
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
