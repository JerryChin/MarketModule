package com.hc.library.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.base.BaseApplication;


/**
 * Created by xc on 2016/8/5.
 */
public interface ModuleInterface {
    Drawable getDrawableCompat(@DrawableRes int resId);
    int getColorCompat(@ColorRes int resId);
    ColorStateList getColorStateListCompat(@ColorRes int resId);
    View inflateView(@LayoutRes int resId, ViewGroup parent);
    <V extends View> V inflateView(@LayoutRes int resId, ViewGroup parent, Class<V> viewType);
    View inflateView(@LayoutRes int resId, ViewGroup parent, boolean attachToRoot);
    <V extends View> V inflateView(@LayoutRes int resId, ViewGroup parent, boolean attachToRoot, Class<V> viewType);


    BaseApplication getBaseApp();
    View findViewById(@IdRes int id);
    <V extends View> V findViewById(@IdRes int id, Class<V> viewType);
}