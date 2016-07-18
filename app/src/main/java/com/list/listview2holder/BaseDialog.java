package com.list.listview2holder;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;



public class BaseDialog extends Dialog {

    protected String errCode;
    protected ConfirmClickListener listener;

    public BaseDialog(Activity activity) {
        super(activity, R.style.YxDialog_Alert);
    }

    public BaseDialog(Context context) {
        super(context, R.style.YxDialog_Alert);
    }

    public BaseDialog(Context context, int theme) {
        super(context, R.style.YxDialog_Alert);
    }

    public BaseDialog(Context context, ConfirmClickListener dialogBtnClickListener) {
        super(context, R.style.YxDialog_Alert);
        listener = dialogBtnClickListener;
    }

    public void setListener(ConfirmClickListener listener) {
        this.listener = listener;
    }

    public String getDialogName() {
        return getClass().getSimpleName();
    }

}

interface ConfirmClickListener {
    void onOKBtnClick();
    void onCancelBtnClick();
}
