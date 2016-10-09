package com.hc.library.util;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by xc on 2016/9/19.
 */
public interface LifeCycle {
    void onCreate(@Nullable Bundle savedInstanceState);
    void onPostCreate(@Nullable Bundle savedInstanceState);
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();

    void onProInitView();
    void initView();
    void onPostInitView();
}
