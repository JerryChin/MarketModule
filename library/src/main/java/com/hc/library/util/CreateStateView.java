package com.hc.library.util;

import android.view.View;

/**
 * Created by xc on 2016/8/10.
 */
public interface CreateStateView {

    View onCreateErrorView();

    View onCreateEmptyView();

    View onCreateLoadingView();

    void showErrorView();

    void showEmptyView();

    void showLoadingView();

    void showContentView();
}
