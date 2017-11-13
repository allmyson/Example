package com.lixue.www.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lixue.www.example.base.BaseActivity;
import com.lixue.www.example.util.StringUtil;

public class MainActivity extends BaseActivity {
    private Button xzBtn, tkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        xzBtn = getView(R.id.btn_xz);
        tkBtn = getView(R.id.btn_tk);
        xzBtn.setOnClickListener(this);
        tkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_xz:
                if (StringUtil.isCanUse()) {
                    startActivity(new Intent(mContext, XZActivity.class));
                }
                break;
            case R.id.btn_tk:
                if (StringUtil.isCanUse()) {
                    startActivity(new Intent(mContext, TKActivity.class));
                }
                break;
        }
    }
}
