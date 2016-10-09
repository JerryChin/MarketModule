package com.hc.library.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * Created by xc on 2016/8/6.
 */
public interface TitleAction<R extends TitleAction> extends TextItem<TitleAction>{
    OnActionListener getActionListener();
    R setOnActionListener(OnActionListener l);
    R setIcon(@DrawableRes int resId);
    R setIcon(Drawable drawable);
    Drawable getIcon();
    R setActionView(View view);
    View getActionView();
    int getPosition();
    void onDestroy();

    interface OnActionListener{
        /**
         * isfinish
         * */
        boolean onAction(View view, int position);
    }
}
