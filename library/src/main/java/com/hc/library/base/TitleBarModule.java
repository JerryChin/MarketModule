package com.hc.library.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hc.library.module.ActivityModule;
import com.hc.library.util.Compat;
import com.hc.library.util.NetConnectionUtils;
import com.hc.library.util.ScreenUtils;
import com.hc.library.util.ViewCompat;
import com.hc.library.widget.TitleBar;
import com.hc.library.widget.menu.OptionMenuTitleAction;


/**
 * Created by xc on 2016/8/6.
 */
final class TitleBarModule extends ActivityModule {
    private OptionMenuTitleAction mMenuAction;
    private boolean mShowNetStatebar = false;
    private LinearLayout mTopLayout;
    private TitleBar mTitlebar;
    private View mStateView;
    private FrameLayout mFlRootView;
    private boolean mIsClip = true;

    private final static int [] ATTRS  = {android.support.v7.appcompat.R.attr.colorPrimaryDark};

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Compat.removeOnGlobalLayoutListener(mTopLayout, this);
            getContentView().setPadding(0, mTopLayout.getHeight(), 0, 0);
        }
    };

    public TitleBarModule(Activity activity,boolean isClip) {
        super(activity);
        mIsClip = isClip;
    }


//    public OptionMenuTitleAction getMenuAction(){
//        return mMenuAction;
//    }

//    public void setShowNetStateBar(boolean isShow) {
//        mShowNetStatebar = isShow;
//        if (mTitlebar != null) {
//            mTitlebar.setNetStateBarVisibility(isShow && getBaseApp().getNetState()
//                    != NetConnectionUtils.NETWORN_NONE ?
//                    View.VISIBLE : View.GONE);
//        }
//    }

    @SuppressLint("InflateParams")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTopLayout = new LinearLayout(getContext());
        mTopLayout.setOrientation(LinearLayout.VERTICAL);
        mTopLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));

        mTitlebar = new TitleBar(getActivity());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            mStateView = new View(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ScreenUtils.getStatusHeight(getContext()));

            TypedArray a = getActivity().obtainStyledAttributes(ATTRS);
            ViewCompat.setBackground(mStateView,a.getDrawable(0));
            a.recycle();

            mStateView.setLayoutParams(params);
            mTopLayout.addView(mStateView);
            mTopLayout.addView(mTitlebar);
        } else {
            mTopLayout.addView(mTitlebar);
        }

        ViewGroup viewGroup = super.getRootView();

        if(mIsClip) {
            viewGroup.addView(mTopLayout,0);
        }else{
            viewGroup.addView(mTopLayout);
            replaceRootView();
        }
    }


    /**
     * 替换root视图，把LinerLayout替换为FrameLayout
     * */
    @SuppressWarnings("ResourceType")
    private void replaceRootView() {
        mFlRootView = new FrameLayout(getActivity());

        int paddingBottom = ScreenUtils.checkDeviceHasNavigationBar(getContext())
                ? ScreenUtils.getNavigationBarHeight(getContext()) : 0;
        int paddingTop = Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                ? ScreenUtils.getStatusHeight(getContext()) : 0;

        mFlRootView.setPadding(0, paddingTop, 0, paddingBottom);

        LinearLayout rootView = (LinearLayout) super.getRootView();
        int count = rootView.getChildCount();
        View[] views = new View[count];
        for (int i = 0; i < count; i++) {
            views[i] = rootView.getChildAt(i);
        }

        rootView.removeAllViews();
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        int index = viewGroup.indexOfChild(rootView);
        viewGroup.removeView(rootView);

        for (int i = 0; i < count; i++) {
            mFlRootView.addView(views[i]);
        }

        viewGroup.addView(mFlRootView, index);

        setContentViewClip(false);

        //contentView刷新布局
        topViewOnLayout();
    }

    private void topViewOnLayout() {
        if(!mIsClip) {
            mTopLayout.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    public void setContentViewClip(boolean isClip){
        ViewGroup contentView = getContentView();
        contentView.setClipChildren(isClip);
        contentView.setClipToPadding(isClip);
    }

    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        mTitlebar.setBackButtonVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setStateBarBackground(Drawable background){
        ViewCompat.setBackground(mStateView,background);
    }

    public void addTopView(View view) {
        topViewOnLayout();
        mTopLayout.addView(view);
    }

    public void removeTopView(View view) {
        topViewOnLayout();
        mTopLayout.removeView(view);
    }


    public void addAction(final TitleAction action) {
        mTitlebar.addAction(action);
        if(action instanceof OptionMenuTitleAction){
            mMenuAction = (OptionMenuTitleAction) action;
        }
    }

    public void removeAction(TitleAction action) {
        mTitlebar.removeAction(action);
        if(action.equals(mMenuAction)){
            mMenuAction = null;
        }
    }

    public void clearAction() {
        mTitlebar.removeAllViews();
        if(mMenuAction != null){
            mMenuAction = null;
        }
    }

    public void setTitleBarBackground(Drawable drawable) {
        ViewCompat.setBackground(mTitlebar, drawable);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStateBarBackground(drawable);
        }
    }

    public void setTitleBarBackground(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        setTitleBarBackground(drawable);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStateBarBackground(drawable);
        }
    }

    public void setTitle(CharSequence title) {
        mTitlebar.setTitle(title);
    }

    public void setTitleTextColor(int color) {
        mTitlebar.setTitleTextColor(color);
    }


    public TitleBar getTitleBar() {
        return mTitlebar;
    }


    @Override
    public ViewGroup getRootView() {
        return mIsClip ? super.getRootView() : mFlRootView;
    }

    public void onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mMenuAction.toggleMenu();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        int netState = getBaseApp().getNetState();
//        netStateChanged(netState);
//
//        getBaseApp().updateNetworkState();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMenuAction != null) {
            mMenuAction.onDestroy();
        }
    }

    @Override
    public void onNetStateChanged(int networkState) {
        if (getInterface() != null) {
            //如果activity在前台
            if (!getInterface().isBackgroundWorker()) {
                if (mTitlebar != null) {
                    netStateChanged(networkState);
                }
            }
        }

    }

    private void netStateChanged(int state) {
        topViewOnLayout();
        if (mShowNetStatebar) {
            switch (state) {
                case NetConnectionUtils.NETWORN_WIFI:
                case NetConnectionUtils.NETWORN_MOBILE:
                    mTitlebar.setNetStateBarVisibility(View.GONE);
                    break;
                case NetConnectionUtils.NETWORN_NONE:
                    mTitlebar.setNetStateBarVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
