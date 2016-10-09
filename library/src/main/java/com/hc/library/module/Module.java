package com.hc.library.module;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.base.BaseApplication;
import com.hc.library.util.ModuleInterface;


/**
 * Created by xc on 2016/8/5.
 */
public abstract class Module implements ModuleInterface {
    private Context mContext;

    public Module(Context context){
        mContext = context;
        onCreate();
    }


    public void onCreate(){
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public Drawable getDrawableCompat(@DrawableRes int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mContext.getDrawable(resId);
        }else{
            return mContext.getResources().getDrawable(resId);
        }
    }

    @Override
    public int getColorCompat(@ColorRes int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getColor(resId);
        }else{
            return mContext.getResources().getColor(resId);
        }
    }

    @Override
    public ColorStateList getColorStateListCompat(@ColorRes int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getColorStateList(resId);
        }else{
            return mContext.getResources().getColorStateList(resId);
        }
    }

    @Override
    public View inflateView(@LayoutRes int resId,ViewGroup parent){
        return LayoutInflater.from(mContext).inflate(resId,parent);
    }
    @Override
    public <V extends View> V inflateView(@LayoutRes int resId,ViewGroup parent,Class<V> viewType){
        return castView(inflateView(resId,parent),viewType);
    }

    @Override
    public View inflateView(@LayoutRes int resId,ViewGroup parent,boolean attachToRoot){
        return LayoutInflater.from(mContext).inflate(resId,parent,attachToRoot);
    }

    @Override
    public <V extends View> V inflateView(@LayoutRes int resId,ViewGroup parent,boolean attachToRoot,Class<V> viewType){
        return castView(inflateView(resId,parent,attachToRoot),viewType);
    }

    public void onDestroy(){
        mContext = null;
    }

    public void sendMessage(Message msg){

    }

    public void dispatchMessage(Message msg){

    }

    protected  <V extends View> V castView(View view,Class<V> viewType){
        return view == null ? null : viewType.cast(view);
    }
}
