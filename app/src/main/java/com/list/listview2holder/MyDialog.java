package com.list.listview2holder;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog extends BaseDialog implements OnClickListener {
    protected TextView tvConfirmTitle, tvConfirmOk, tvConfirmCancel, tvConfirmContent, tvConfirmContent2,tvTitle;
    protected LinearLayout llContent;
    protected EditText etAddName;
    private View divider_vertical;

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, ConfirmClickListener listener) {
        super(context, listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_base_confirm_dialog);

        tvConfirmTitle = (TextView) findViewById(R.id.tv_confirm_title);

        divider_vertical = findViewById(R.id.divider_vertical);

        tvConfirmContent = (TextView) findViewById(R.id.tv_confirm_content);
        tvConfirmContent2 = (TextView) findViewById(R.id.tv_confirm_content_2);

        tvConfirmOk = (TextView) findViewById(R.id.tv_confirm_ok);
        tvConfirmOk.setOnClickListener(this);

        tvConfirmCancel = (TextView) findViewById(R.id.tv_confirm_cancel);
        tvConfirmCancel.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setOnClickListener(this);

        llContent = (LinearLayout) findViewById(R.id.ll_content);
        llContent.setOnClickListener(this);

        etAddName = (EditText) findViewById(R.id.et_addbookname);

        etAddName.setPadding(20,0,0,0);

        init();
    }

    private void init() {
        tvConfirmOk.setText("确定");
        tvConfirmCancel.setText("取消");
    }

    /**
     * 设置触摸对话框以外的地方取消对话框  true:点击dialog之外关闭  false:点击dialog之外不关闭
     */
    public void showDialog() {
        setCanceledOnTouchOutside(false);
        show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_confirm_cancel:
                if (null != listener)
                    listener.onCancelBtnClick();
                dismiss();
                break;
            case R.id.tv_confirm_ok:

                if (null != listener)
                    listener.onOKBtnClick();
                dismiss();
                break;
        }
    }

    /**
     *
     * 设置标题和内容
     * @param title
     * @param message
     */
    public void show(String title, String message) {
        if (!isShowing())
            show();
        tvConfirmTitle.setText(title);
        tvConfirmContent.setText(message);
    }

    /**
     * 只有一个“确定”按钮的对话框
     */
    public void showConfirm(String title, String message){
        if (!isShowing())
            show();
        divider_vertical.setVisibility(View.GONE);
        tvConfirmCancel.setVisibility(View.GONE);
        tvConfirmTitle.setText(title);
        tvConfirmContent.setText(message);
    }

    public void showError(String errCode, String errorMessage) {
        this.errCode = errCode;
        super.show();
        if (!TextUtils.isEmpty(errorMessage)) {
            tvConfirmContent.setText(errorMessage);
        } else {
            tvConfirmContent.setText("您的网络不是很给力，请您稍后再试");
        }

    }
}