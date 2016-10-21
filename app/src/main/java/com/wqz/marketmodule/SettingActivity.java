package com.wqz.marketmodule;

import android.os.Bundle;

import com.hc.library.base.TitleBarActivity;

public class SettingActivity extends TitleBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle("设置");
    }
}
