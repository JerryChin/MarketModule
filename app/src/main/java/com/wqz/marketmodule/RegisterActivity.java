package com.wqz.marketmodule;

import android.os.Bundle;

import com.hc.library.base.TitleBarActivity;

public class RegisterActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("注册");
    }
}
