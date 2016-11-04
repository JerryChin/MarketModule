package com.wqz.marketmodule;

/**
 * Created by Wqz on 2016/10/8.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.hc.library.base.BaseActivity;
import com.hc.library.module.Module;
import com.hc.library.pojo.Carousel;
import com.hc.library.pojo.User;
import com.hc.library.util.IViewGroup;
import com.hc.thirdpartylibrary.NumberProgressBar;
import com.hc.thirdpartylibrary.OnProgressBarListener;
import com.squareup.picasso.Picasso;
import com.wqz.app.MarketAPP;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SpalshActivity extends BaseActivity
{
    NumberProgressBar npbSplash;
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        initUI();


    }

    void initUI()
    {
        npbSplash = (NumberProgressBar)findViewById(R.id.npb_splash);
        new Thread(new ProgressThread()).start();

        ivLogo = (ImageView)findViewById(R.id.iv_splashlogo);

        OkHttpUtils.get().url(Utils.PRODUCT_GET_CAROUSEL)
                .build() .execute(carouselCallback);

        SharedPreferences sharedPreferences = getSharedPreferences("MarketModule",
                Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        String account = sharedPreferences.getString("account","");
        String password = sharedPreferences.getString("password","");

        if(account.isEmpty() || password.isEmpty()) return;

        OkHttpUtils.get().url(Utils.USER_LOGIN)
                .addParams("account", account)
                .addParams("password", password)
                .build() .execute(loginCallback);

    }

    StringCallback loginCallback = new StringCallback()
    {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(SpalshActivity.this,"Error Code : " + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String response, int id) {
            if(response.equals(""))
                Toast.makeText(SpalshActivity.this,"账号密码错误，请重新输入",Toast.LENGTH_LONG).show();
            else
            {
                User user = new Gson().fromJson(response, User.class);
                getBaseApp().setUser(user);
            }
        }
    };

    StringCallback carouselCallback = new StringCallback()
    {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(SpalshActivity.this,"Error Code : " + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String response, int id)
        {
            if(response.equals(""))
                Log.e("SpalshActivity","轮播图片获取错误");
            else
            {
                final String json = response;
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Carousel[] carousels = new Carousel[5];
                        Drawable[] drawables = new Drawable[5];

                        try
                        {
                            JSONArray carouselArray = new JSONArray(json);

                            for(int i = 0;i < carousels.length;i++)
                            {
                                carousels[i] = new Gson().fromJson(carouselArray.get(i).toString(), Carousel.class);
                                Picasso picasso = Picasso.with(SpalshActivity.this);
                                String imageAddress = carousels[i].getImageaddress();
                                Bitmap bmp = picasso.load(imageAddress).get();
                                drawables[i] = new BitmapDrawable(bmp);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ((MarketAPP)getBaseApp()).setCarousel(drawables);
                    }
                }).start();
            }
        }
    };

    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    npbSplash.setProgress((int)msg.obj);
                    if((int)msg.obj == 100)
                    {
                        RotateAnimation animation =new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,
                                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                        animation.setDuration(1000);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                startActivity(new Intent(SpalshActivity.this,MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        ivLogo.startAnimation(animation);
                    }
                    break;
            }
        }
    };

    class ProgressThread implements Runnable
    {
        @Override
        public void run() {
            for(int i = 0;i <= 50;i++)
            {
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = i * 2;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
