package com.hc.library.module;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hc.library.base.BaseApplication;
import com.hc.library.util.AMInterface;
import com.hc.library.util.ActivityInterface;
import com.hc.library.util.ActivityLifeCycle;
import com.hc.library.util.CreateStateView;
import com.hc.library.util.IViewGroup;
import com.hc.library.util.ModuleInterface;
import com.hc.library.util.OnNetStateChangedListener;
import com.hc.library.widget.DefaultCreateStateViewImpl;


/**
 * Created by xc on 2016/8/5.
 */
public class ActivityModule extends Module implements ActivityInterface,ActivityLifeCycle,OnNetStateChangedListener, CreateStateView {
    private Activity mActivity;
    private ViewGroup mDecorView;
    private ViewGroup mRootView;
    private ViewGroup mContentView;
    private AMInterface mAMInterface;

    private CreateStateView mCreateStateView;

    public ActivityModule(Activity activity) {
        super(activity.getApplicationContext());
        mActivity = activity;

        mCreateStateView = new DefaultCreateStateViewImpl(getContext(), getInterface(), new IViewGroup() {
            @Override
            public void addView(View view) {
                getContentView().addView(view);
            }

            @Override
            public void removeView(View view) {
                getContentView().removeView(view);
            }

            @Override
            public int indexOfChild(View view) {
                return getContentView().indexOfChild(view);
            }
        });
    }

    public Activity getActivity(){
        return mActivity;
    }

    @Override
    public void onProInitView() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void onPostInitView() {
    }


    public void showErrorView(){
        mCreateStateView.showErrorView();
    }

    public void showEmptyView(){
        mCreateStateView.showEmptyView();
    }

    public void showLoadingView(){
        mCreateStateView.showLoadingView();
    }

    public void showContentView(){
        mCreateStateView.showContentView();
    }

    @Override
    public ViewGroup getDecorView() {
        if(mDecorView == null){
            mDecorView = (ViewGroup) mActivity.getWindow().getDecorView();
        }
        return mDecorView;
    }

    @Override
    public ViewGroup getRootView(){
        if(mRootView == null){
            mRootView = (LinearLayout) getDecorView().getChildAt(0);
        }
        return mRootView;
    }

    @Override
    public ViewGroup getContentView(){
        if(mContentView == null) {
            mContentView = (ViewGroup) getRootView().findViewById(android.R.id.content);
        }
        return mContentView;
    }

    @Override
    public <V extends View> V findViewById(@IdRes int id, Class<V> viewType){
        return castView(mActivity.findViewById(id),viewType);
    }

    public View findViewById(@IdRes int id){
        return mActivity.findViewById(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    public void onPostCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mActivity = null;
        mRootView = null;
        mContentView = null;
        mAMInterface = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void sendMessage(Message msg) {
        if(getInterface() != null){
            getInterface().sendMessageToModular(msg);
        }
    }

    public AMInterface getInterface(){
        if(mActivity instanceof AMInterface){
            mAMInterface = (AMInterface)mActivity;
        }
        return mAMInterface;
    }

    private BaseApplication mBaseApp;

    @Override
    public BaseApplication getBaseApp(){
        if(mBaseApp == null) {
            if (mActivity instanceof ModuleInterface) {
                mBaseApp = ((ModuleInterface) mActivity).getBaseApp();
            }
        }

        return mBaseApp;
    }

    @Override
    public void onNetStateChanged(int networkState) {
    }

    @Override
    public View onCreateErrorView() {
        return mCreateStateView.onCreateErrorView();
    }

    @Override
    public View onCreateEmptyView() {
        return mCreateStateView.onCreateEmptyView();
    }

    @Override
    public View onCreateLoadingView() {
        return mCreateStateView.onCreateLoadingView();
    }
}
