package com.wqz.marketmodule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hc.library.base.BaseFragmentActivity;
import com.hc.library.base.TitleBarActivity;
import com.hc.library.widget.warpper.Toast;
import com.wqz.app.MarketAPP;

public class SettingActivity extends TitleBarActivity
{
    Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle("设置");
        setTitleTextColor(R.color.colorIOSBlue);
        setTitleBarBackground(R.color.colorWhite);

        initUI();
    }

    void initUI()
    {
        btnQuit = (Button)findViewById(R.id.btn_quit);
        btnQuit.setOnClickListener(l);
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_quit:
                    SharedPreferences sharedPreferences=getSharedPreferences("MarketModule",
                            Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);

                    SharedPreferences.Editor editor = sharedPreferences.edit(); //获取编辑器
                    editor.putString("account","");
                    editor.putString("password","");
                    editor.apply();//提交修改
                    (SettingActivity.this.getBaseApp()).setUser(null);

                    Toast.makeText(SettingActivity.this,"已退出登录",Toast.LENGTH_LONG).show();
                    SettingActivity.this.finish();
                    break;
            }
        }
    };
}
