package com.hc;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.hc.library.base.BaseApplication;
import com.hc.library.util.Assets;
import com.hc.library.util.L;

import java.nio.charset.Charset;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<BaseApplication> {
    public ApplicationTest() {
        super(BaseApplication.class);
    }

    @SmallTest
    public void testAssetsDecode(){
        byte[]bytes = "士大夫似的".getBytes(Charset.forName("UTF-8"));
        Assets.decode(bytes);
        L.i(new String(bytes));
    }
}