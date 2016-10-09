package com.hc.library.util;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hc.library.base.BaseApplication;

/**
 * Created by xc on 2016/8/5.
 */
public interface ActivityInterface extends ModuleInterface{
    ViewGroup getDecorView();
    ViewGroup getRootView();
    ViewGroup getContentView();
}
