package com.wqz.marketmodule;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hc.library.base.TitleBarActivity;
import com.hc.library.pojo.Product;
import com.squareup.picasso.Picasso;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ProductShowActivity extends TitleBarActivity
{

    ListView lv;
    List<Product> pl = new ArrayList<Product>();
    List<Map<String, Object>> list;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);

        setTitle("物品详情");
        setTitleTextColor(R.color.colorIOSBlue);
        setTitleBarBackground(R.color.colorWhite);

        lv = (ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(listener);

        showProgressDialog();

        Bundle bundle = this.getIntent().getExtras();
        if(bundle.getBoolean("isSerach"))
        {
            StringBuilder urlBulider = new StringBuilder();
            urlBulider.append(Utils.PRODUCT_GET_SERACH );
            urlBulider.append("?serach=").append(bundle.getString("serach"))
                    .append("&start=0&size=100");
            //Serach进来的。
            OkHttpUtils.get().url(urlBulider.toString())
                    .build().execute(getProductCallback);
        }
        else
        {
            //Classify进来的
            OkHttpUtils.get().url(Utils.PRODUCT_GET_SERACH)
                    .addParams("serach", "套餐")
                    .addParams("start", "0")
                    .addParams("size", "20")
                    .build().execute(getProductCallback);
        }
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int pos, long id)
        {
            Toast.makeText(ProductShowActivity.this, "You Click:" + pos, Toast.LENGTH_LONG).show();
        }
    };

    StringCallback getProductCallback = new StringCallback()
    {
        @Override
        public void onError(Call call, Exception e, int id)
        {
            Toast.makeText(ProductShowActivity.this,"Error Code : " + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String response, int id)
        {
            pl.clear();
            try
            {
                JSONArray productList = new JSONArray(response);
                for(int i = 0;i < productList.length();i++)
                {
                    pl.add(new Gson().fromJson(productList.get(i).toString(),Product.class));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    list = new ArrayList<Map<String, Object>>();

                    for(int i = 0;i < pl.size();i++)
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("title", pl.get(i).getName());
                        map.put("info", pl.get(i).getPrice());
                        try {
                            map.put("img", Picasso.with(ProductShowActivity.this)
                                        .load(pl.get(i).getImageaddress()).get());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        list.add(map);
                    }

                    myHandler.sendMessage(new Message());
                }
            }).start();
        }
    };

    class MyHandler extends Handler
    {
        public MyHandler() {}

        public MyHandler(Looper L)
        {
            super(L);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            SimpleAdapter adapter = new SimpleAdapter(ProductShowActivity.this,list,R.layout.v_list,
                    new String[]{"title","info","img"},
                    new int[]{R.id.title,R.id.info,R.id.img});
            adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object bitmapData, String s) {
                if(view instanceof ImageView && bitmapData instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) bitmapData);
                    return true;
                }
                return false;
            }});
            lv.setAdapter(adapter);

            closeProgressDialog();
        }
    }MyHandler myHandler = new MyHandler();



    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ProductShowActivity.this);
            progressDialog.setMessage("正在搜索结果。。。");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
