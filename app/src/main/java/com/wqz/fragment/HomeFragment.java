package com.wqz.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hc.library.base.BaseActivity;
import com.hc.library.base.BaseApplication;
import com.hc.library.base.BaseFragmentActivity;
import com.hc.library.pojo.Carousel;
import com.hc.library.widget.SlidingSwitcherView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wqz.marketmodule.R;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 首页
 * @author Wqz
 * */
public class HomeFragment extends BaseFragment
{
    SlidingSwitcherView ssv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, int flag) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ssv = (SlidingSwitcherView)view.findViewById(R.id.sliding_switcher_view);

        return view;
    }




}
