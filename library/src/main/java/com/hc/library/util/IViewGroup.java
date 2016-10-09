package com.hc.library.util;

import android.view.View;

/**
 * Created by xc on 2016/8/10.
 */
public interface IViewGroup {
    void addView(View view);
    void removeView(View view);
    int indexOfChild(View view);
}
