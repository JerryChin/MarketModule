package com.wqz.marketmodule;

import android.os.Bundle;

import com.hc.library.base.TitleBarActivity;

public class AboutActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("关于我");
        setTitleTextColor(R.color.colorIOSBlue);
        setTitleBarBackground(R.color.colorWhite);
    }
}
