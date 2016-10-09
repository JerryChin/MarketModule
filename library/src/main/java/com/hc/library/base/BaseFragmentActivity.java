package com.hc.library.base;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.R;
import com.hc.library.module.ActivityModule;
import com.hc.library.module.Module;
import com.hc.library.util.AMInterface;
import com.hc.library.util.ActivityInterface;
import com.hc.library.util.ActivityLifeCycle;
import com.hc.library.util.LifeCycle;
import com.hc.library.util.OnNetStateChangedListener;
import com.hc.library.util.SystemUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/4.
 */
public class BaseFragmentActivity extends FragmentActivity implements ActivityInterface, AMInterface, OnNetStateChangedListener {
    private ActivityModule mMainModule;
    private ArrayList<Module> mModules;
    private boolean mIsBackgroundWorker;
    private BaseApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getBaseApp() != null) {
            getBaseApp().addActivity(this);
        }
        super.onCreate(savedInstanceState);
        SystemUtils.setMiuiStatusBarDarkMode(this, true);

        mModules = new ArrayList<>();
        mModules.add(getMainModule());

        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onCreate(savedInstanceState);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onPostCreate(savedInstanceState);
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        innerInitView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        innerInitView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        innerInitView();
    }

    private void innerInitView() {
        onProInitView();
        initView();
        onPostInitView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        for (Module module : mModules) {
            ActivityLifeCycle lifeCycle = getActivityLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onRestart();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onStart();
            }
        }
    }

    @Override
    protected void onResume() {
        mIsBackgroundWorker = false;
        super.onResume();

        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onResume();
            }
        }

    }

    @Override
    protected void onPause() {
        mIsBackgroundWorker = true;
        super.onPause();

        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onPause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onStop();
            }
        }

    }


    /**
     * 创建主模块
     */
    protected
    @NonNull
    ActivityModule onCreateMainModular() {
        return new ActivityModule(this);
    }

    /**
     * 添加模块
     */
    public void addModule(Module module) {
        mModules.add(module);
    }

    public Module getModule(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return mModules.get(index + 1);
    }


    @Override
    public
    @NonNull
    ActivityModule getMainModule() {
        if (mMainModule == null) {
            mMainModule = onCreateMainModular();
        }

        return mMainModule;
    }

    @Override
    protected void onDestroy() {
        if (getBaseApp() != null) {
            getBaseApp().removeActivity(this);
        }
        super.onDestroy();

        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onDestroy();
            }
        }

        mMainModule = null;
    }

    public void onProInitView() {
        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onProInitView();
            }
        }
    }

    public void initView() {
        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.initView();
            }
        }
    }

    public void onPostInitView() {
        for (Module module : mModules) {
            LifeCycle lifeCycle = getLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onPostInitView();
            }
        }
    }

    @Override
    public ViewGroup getDecorView() {
        return mMainModule.getDecorView();
    }

    @Override
    public ViewGroup getRootView() {
        return mMainModule.getRootView();
    }

    @Override
    public ViewGroup getContentView() {
        return mMainModule.getContentView();
    }

    @Override
    public <V extends View> V findViewById(@IdRes int id, Class<V> viewType) {
        return mMainModule.findViewById(id, viewType);
    }

    @Override
    public BaseApplication getBaseApp() {
        if (mApp != null) {
            return mApp;
        }
        if (getApplication() instanceof BaseApplication) {
            mApp = (BaseApplication) getApplication();
        }

        return mApp;
    }

    @Override
    public Drawable getDrawableCompat(@DrawableRes int resId) {
        return mMainModule.getDrawableCompat(resId);
    }

    @Override
    public int getColorCompat(@ColorRes int resId) {
        return mMainModule.getColorCompat(resId);
    }

    @Override
    public ColorStateList getColorStateListCompat(@ColorRes int resId) {
        return mMainModule.getColorStateListCompat(resId);
    }

    @Override
    public View inflateView(@LayoutRes int resId, ViewGroup parent) {
        return mMainModule.inflateView(resId, parent);
    }

    @Override
    public <V extends View> V inflateView(@LayoutRes int resId, ViewGroup parent, Class<V> viewType) {
        return mMainModule.inflateView(resId, parent, viewType);
    }

    @Override
    public View inflateView(@LayoutRes int resId, ViewGroup parent, boolean attachToRoot) {
        return mMainModule.inflateView(resId, parent, attachToRoot);
    }

    @Override
    public <V extends View> V inflateView(@LayoutRes int resId, ViewGroup parent, boolean attachToRoot, Class<V> viewType) {
        return mMainModule.inflateView(resId, parent, attachToRoot, viewType);
    }

    public synchronized void sendMessageToModular(int what, int targetIndex, Bundle data) {
        com.hc.library.module.Message msg = new com.hc.library.module.Message(what, targetIndex);
        msg.putAll(data);
        getModule(targetIndex).dispatchMessage(msg);
    }

    public void sendMessageToModular(com.hc.library.module.Message msg) {
        getModule(msg.targetIndex).dispatchMessage(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Module module : mModules) {
            ActivityLifeCycle lifeCycle = getActivityLifeCycle(module);
            if (lifeCycle != null) {
                lifeCycle.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onNetStateChanged(int networkState) {
        for (Module module : mModules) {
            if (module instanceof OnNetStateChangedListener) {
                ((OnNetStateChangedListener) module).onNetStateChanged(networkState);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


    @Override
    public View onCreateErrorView() {
        return mMainModule.onCreateErrorView();
    }

    @Override
    public View onCreateEmptyView() {
        return mMainModule.onCreateEmptyView();
    }

    @Override
    public View onCreateLoadingView() {
        return mMainModule.onCreateLoadingView();
    }

    @Override
    public final void showErrorView() {
        mMainModule.showErrorView();
    }

    @Override
    public final void showEmptyView() {
        mMainModule.showEmptyView();
    }

    @Override
    public final void showLoadingView() {
        mMainModule.showLoadingView();
    }

    @Override
    public final void showContentView() {
        mMainModule.showContentView();
    }

    @Override
    public boolean isBackgroundWorker() {
        return mIsBackgroundWorker;
    }


    private LifeCycle getLifeCycle(@NonNull Module module) {
        if (module instanceof LifeCycle) {
            return (LifeCycle) module;
        } else {
            return null;
        }
    }

    private ActivityLifeCycle getActivityLifeCycle(@NonNull Module module) {
        if (module instanceof ActivityLifeCycle) {
            return (ActivityLifeCycle) module;
        } else {
            return null;
        }
    }
}
