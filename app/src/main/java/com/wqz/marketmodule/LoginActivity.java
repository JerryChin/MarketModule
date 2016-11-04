package com.wqz.marketmodule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hc.library.base.TitleBarActivity;
import com.hc.library.pojo.User;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class LoginActivity extends TitleBarActivity
{
    TextView tvRegister;
    TextView tvForget;
    Button btnLogin;
    EditText etAccount;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("登录");
        setTitleBarBackground(R.color.colorNightSky);
        setTitleTextColor(Color.WHITE);

        initUI();
    }

    void initUI()
    {

        tvRegister = (TextView)findViewById(R.id.tv_register_activity);
        tvRegister.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        tvRegister.getPaint().setAntiAlias(true);//抗锯齿
        tvRegister.setOnClickListener(l);

        tvForget = (TextView)findViewById(R.id.tv_register_forget);
        tvForget.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        tvForget.getPaint().setAntiAlias(true);//抗锯齿
        tvForget.setOnClickListener(l);

        btnLogin = (Button)findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(l);

        etAccount = (EditText)findViewById(R.id.et_login_account);
        etPassword = (EditText)findViewById(R.id.et_login_password);
    }

    View.OnClickListener l = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.tv_register_activity:
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    break;
                case R.id.tv_register_forget:
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    break;
                case R.id.btn_login_login:
                    OkHttpUtils.get().url(Utils.USER_LOGIN)
                            .addParams("account", etAccount.getText().toString())
                            .addParams("password", etPassword.getText().toString())
                            .build() .execute(loginCallback);
                    break;
            }
        }
    };

    StringCallback loginCallback = new StringCallback()
    {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(LoginActivity.this,"Error Code : " + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String response, int id) {
            if(response.equals(""))
                Toast.makeText(LoginActivity.this,"账号密码错误，请重新输入",Toast.LENGTH_LONG).show();
            else
            {
                User user = new Gson().fromJson(response, User.class);
                getBaseApp().setUser(user);

                SharedPreferences sharedPreferences=getSharedPreferences("MarketModule",
                        Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);

                SharedPreferences.Editor editor = sharedPreferences.edit(); //获取编辑器
                editor.putString("account",etAccount.getText().toString());
                editor.putString("password",etPassword.getText().toString());
                editor.apply();//提交修改

                LoginActivity.this.finish();
            }
        }
    };
}
