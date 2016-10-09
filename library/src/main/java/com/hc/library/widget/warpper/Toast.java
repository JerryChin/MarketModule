package com.hc.library.widget.warpper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.hc.library.R;


/**
 * Created by fs on 2016/3/22.
 */
public class Toast {
    public final static int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;
    public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;

    public static android.widget.Toast makeText(Context context, String text,int duration) {
        TextView mTvMsg = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_toast, null, false);
        mTvMsg.setText(text);

        @SuppressLint("ShowToast") android.widget.Toast toast = android.widget.Toast.makeText(context, text, duration);
        toast.setView(mTvMsg);

        return toast;
    }

    public static android.widget.Toast makeText(Context context, @StringRes int resId,int duration) {
        String text = context.getString(resId);
        return makeText(context, text, duration);
    }
}
