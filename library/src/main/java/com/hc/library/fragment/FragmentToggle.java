package com.hc.library.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by xc on 2016/9/6.
 */
public interface FragmentToggle {
    void onEnter(Fragment enterFragment,Fragment leaveFragment);
    void onLeave(Fragment enterFragment,Fragment leaveFragment);
}
