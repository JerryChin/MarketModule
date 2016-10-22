package com.wqz.marketmodule;

import android.graphics.Color;
import android.os.Bundle;

import com.hc.library.base.TitleBarActivity;

public class LoginActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("登录");
        setTitleBarBackground(R.color.colorNightSky);
        setTitleTextColor(Color.WHITE);
    }
}
