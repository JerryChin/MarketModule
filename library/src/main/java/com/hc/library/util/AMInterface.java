package com.hc.library.util;

import android.support.annotation.NonNull;

import com.hc.library.module.ActivityModule;


/**
 * Created by xc on 2016/8/5.
 * module和activity通信的接口
 */
public interface AMInterface extends CreateStateView,IsBackgroundWorker {
    @NonNull
    ActivityModule getMainModule();

    void sendMessageToModular(com.hc.library.module.Message msg);
}
