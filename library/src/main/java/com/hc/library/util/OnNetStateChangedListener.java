package com.hc.library.util;

/**
 * Created by xc on 2016/8/9.
 */
public interface OnNetStateChangedListener {
    /**
     * @param networkState {@link NetConnectionUtils}
     * */
    void onNetStateChanged(int networkState);
}
