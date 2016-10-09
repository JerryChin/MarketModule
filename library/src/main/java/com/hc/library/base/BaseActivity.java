package com.hc.library.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hc.library.R;
import com.hc.library.util.OnNetStateChangedListener;
import com.hc.library.util.SystemUtils;


/**
 * Created by Wqz on 2016/8/3.
 */
public class BaseActivity extends Activity implements OnNetStateChangedListener {
    private boolean mIsBackgroundWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getBaseApp() != null){
            getBaseApp().addActivity(this);
        }

        super.onCreate(savedInstanceState);
        SystemUtils.setMiuiStatusBarDarkMode(this,true);
    }

    @Override
    protected void onDestroy() {
        if(getBaseApp() != null){
            getBaseApp().removeActivity(this);
        }

        super.onDestroy();
    }

    @Override
    public void onNetStateChanged(int networkState) {
    }

    @Override
    protected void onResume() {
        mIsBackgroundWorker = false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mIsBackgroundWorker = true;
        super.onPause();
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

    private BaseApplication mApp;

    public BaseApplication getBaseApp(){
        if(mApp != null){
            return mApp;
        }
        if(getApplication() instanceof BaseApplication){
            mApp = (BaseApplication) getApplication();
        }

        return mApp;
    }

    public boolean isBackgroundWorker(){
        return mIsBackgroundWorker;
    }
}