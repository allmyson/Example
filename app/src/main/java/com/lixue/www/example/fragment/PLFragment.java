package com.lixue.www.example.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lixue.www.example.R;
import com.lixue.www.example.util.StringUtil;
import com.lixue.www.example.util.ToastUtil;


/**
 * @author lh
 * @version 1.0.0
 * @filename PLFragment
 * @description -------------------------------------------------------
 * @date 2017/7/23 17:27
 */
public class PLFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private EditText et;
    private TextView sureTV;
    private ResultListener listener;


    public static PLFragment newInstance(int style, int theme) {
        PLFragment pFragment = new PLFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);
        return pFragment;
    }

    InputMethodManager inputManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);// 设置点击屏幕Dialog不消失
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
        inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onStart() {
        super.onStart();

        //得到dialog对应的window
        Window window = getDialog().getWindow();
        if (window != null) {
            //得到LayoutParams
            WindowManager.LayoutParams params = window.getAttributes();

            //修改gravity
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.dimAmount = 0.0f;//背景设为透明
            window.setAttributes(params);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = inflater.inflate(R.layout.dialog_pl, null, false);
        et = (EditText) mContentView.findViewById(R.id.et_text);
        sureTV = (TextView) mContentView.findViewById(R.id.tv_sure);
        sureTV.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = et.getText().toString();
                        if (StringUtil.isBlank(text.trim())) {
                            ToastUtil.showToast(getActivity(), "答案不能为空!");
                            return;
                        }
                        if (listener != null) {
                            listener.result(text);
                        }

                        inputManager.hideSoftInputFromWindow(et.getWindowToken(), 0);

                    }
                }
        );

        // 弹出键盘
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                inputManager.showSoftInput(et, 0);
            }
        }, 200);
        return mContentView;
    }

    public interface ResultListener {
        void result(String text);
    }

    public ResultListener getListener() {
        return listener;
    }

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }


}
