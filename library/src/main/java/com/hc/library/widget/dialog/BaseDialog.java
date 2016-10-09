package com.hc.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by xc on 2016/9/12.
 */
public class BaseDialog extends Dialog {
    private SparseArray<View> mViews;

    public BaseDialog(Context context) {
        this(context, com.hc.library.R.style.Dialog);
        init();
    }
    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        this(context, com.hc.library.R.style.Dialog);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
        init();
    }

    protected void init(){
    }

    @Override
    public View findViewById(int id) {
        View view = mViews == null ? null : mViews.get(id);
        view = view == null ? super.findViewById(id) : view;
        if(view != null){
            if(mViews == null) {
                mViews = new SparseArray<>();
            }
            mViews.put(id,view);
        }

        return view;
    }

    public <V extends View> V findViewById(int id,@NonNull Class<V>viewType) {
        View view = findViewById(id);
        return view == null ? null : viewType.cast(view);
    }
}
