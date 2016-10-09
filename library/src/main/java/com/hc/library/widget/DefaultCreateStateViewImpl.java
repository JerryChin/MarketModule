package com.hc.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.hc.library.R;
import com.hc.library.util.CreateStateView;
import com.hc.library.util.IViewGroup;


/**
 * Created by xc on 2016/8/10.
 */
public class DefaultCreateStateViewImpl implements CreateStateView {
    private final static int VIEW_CONTENT = 1;
    private final static int VIEW_ERROR = 2;
    private final static int VIEW_EMPTY = 3;
    private final static int VIEW_LOADING = 4;

    private int mCurrentView = VIEW_CONTENT;

    private AlphaAnimation mHideAnim;
    private AlphaAnimation mShowAnim;

    private View mErrorView;
    private View mEmptyView;
    private View mLoadingView;

    private Context mContext;
    private CreateStateView mCreateStateView;
    private IViewGroup mContentView;

    public DefaultCreateStateViewImpl(Context context, CreateStateView impl, IViewGroup contentView){
        mContext = context;
        mCreateStateView = impl;
        mContentView = contentView;
    }

    /**
     * 创建错误视图(请求错误时显示)
     * */
    @SuppressLint("InflateParams")
    @Override
    public View onCreateErrorView(){
        if(mErrorView != null){
            return mErrorView;
        }else{
            mErrorView = LayoutInflater.from(mContext).inflate(R.layout.layout_error,null,false);
            return mErrorView;
        }
    }

    /**
     * 创建空白视图(请求内容为空时显示)
     * */
    @SuppressLint("InflateParams")
    @Override
    public View onCreateEmptyView(){
        if(mEmptyView != null){
            return mEmptyView;
        }else{
            mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty,null,false);
            return mEmptyView;
        }
    }



    @SuppressLint("InflateParams")
    @Override
    public View onCreateLoadingView(){
        if(mLoadingView != null){
            return mLoadingView;
        }else {
            mLoadingView =  LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null, false);
            return mLoadingView;
        }
    }

    @Override
    public void showErrorView() {
        if(mCurrentView != VIEW_ERROR && mCreateStateView != null) {
            innerHide(VIEW_ERROR);
            View errorView = mCreateStateView.onCreateErrorView();
            if(mContentView.indexOfChild(errorView) == -1) {
                innerShow(errorView);
            }
        }
    }

    @Override
    public void showEmptyView() {
        if(mCurrentView != VIEW_EMPTY && mCreateStateView != null) {
            innerHide(VIEW_EMPTY);
            View emptyView = mCreateStateView.onCreateEmptyView();
            if(mContentView.indexOfChild(emptyView) == -1) {
                innerShow(emptyView);
            }
        }
    }

    @Override
    public void showLoadingView() {
        if(mCurrentView != VIEW_LOADING && mCreateStateView != null) {
            innerHide(VIEW_LOADING);
            View loadingView = mCreateStateView.onCreateLoadingView();
            if(mContentView.indexOfChild(loadingView) == -1) {
                innerShow(loadingView);
            }
        }
    }

    @Override
    public void showContentView() {
        if(mCurrentView != VIEW_CONTENT) {
            innerHide(VIEW_CONTENT);
        }
    }

    private void innerShow(final View view){
        if(view == null){
            return;
        }

        if(mShowAnim == null){
            mShowAnim = new AlphaAnimation(0,1);
            mShowAnim.setDuration(300);
        }

        mShowAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        mContentView.addView(view);
        view.startAnimation(mShowAnim);
    }

    private void innerHide(int view){
        View _view = null;
        switch (mCurrentView) {
            case VIEW_CONTENT:

                break;
            case VIEW_ERROR:
                _view = mCreateStateView.onCreateErrorView();
                break;
            case VIEW_EMPTY:
                _view = mCreateStateView.onCreateEmptyView();
                break;
            case VIEW_LOADING:
                _view = mCreateStateView.onCreateLoadingView();
                break;
        }

        mCurrentView = view;

        if(_view != null) {
            if (mHideAnim == null) {
                mHideAnim = new AlphaAnimation(1, 0);
                mHideAnim.setDuration(300);
            }

            final View final_view = _view;
            mHideAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    mContentView.removeView(final_view);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            _view.startAnimation(mHideAnim);
        }
    }
}
