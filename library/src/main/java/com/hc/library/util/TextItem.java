package com.hc.library.util;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

/**
 * Created by xc on 2016/8/15.
 */
public interface TextItem<R extends TextItem> {
    R setText(@StringRes int resId);
    R setText(String text);
    R setTextColor(int color);
    R setTextColorResource(@ColorRes int colorRes);
    int getTextColor();
    String getText();
}
