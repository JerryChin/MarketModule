package com.wqz.marketmodule;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hc.library.base.TitleBarActivity;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class RegisterActivity extends TitleBarActivity
{
    EditText etRegisterAccount;
    EditText etRegisterPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitleBarBackground(R.color.colorNightRain);
        setTitleTextColor(Color.WHITE);
        setTitle("注册");

        initUI();
    }
    void initUI()
    {
        etRegisterAccount = (EditText)findViewById(R.id.et_register_account);
        etRegisterPassword = (EditText)findViewById(R.id.et_register_password);

        btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(l);
    }

    View.OnClickListener l = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_register:
                    OkHttpUtils.post().url(Utils.USER_REGISTER)
                            .addParams("account", etRegisterAccount.getText().toString())
                            .addParams("password", etRegisterPassword.getText().toString())
                            .build() .execute(registerCallback);
                    break;
            }
        }
    };

    StringCallback registerCallback = new StringCallback()
    {
        @Override
        public void onError(Call call, Exception e, int id)
        {
            Toast.makeText(RegisterActivity.this,"Error Code : " + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String response, int id)
        {
            if(response.equals("0"))
            {
                Toast.makeText(RegisterActivity.this,"注册成功！" ,Toast.LENGTH_LONG).show();
                RegisterActivity.this.finish();
            }
            else if(response.equals("1"))
            {
                Toast.makeText(RegisterActivity.this,"账号重复，请更改后输入" ,Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(RegisterActivity.this,"服务器忙或出错，请联系管理员" ,Toast.LENGTH_LONG).show();
            }
        }
    };
}
