package com.hc.library.util;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xc on 2016/8/5.
 */
public interface ActivityLifeCycle extends LifeCycle{
    void onRestart();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
