package com.wqz.marketmodule;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hc.library.base.TitleBarActivity;
import com.hc.library.pojo.Product;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ProductShowActivity extends TitleBarActivity
{

    ListView lv;
    List<Product> pl = new ArrayList<Product>();

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

        OkHttpUtils.get().url(Utils.PRODUCT_GET_SERACH)
                .addParams("serach", "套餐")
                .addParams("start", "0")
                .addParams("size","20")
                .build() .execute(getProductCallback);
    }

    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i = 0;i < pl.size();i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", pl.get(i).getName());
            map.put("info", pl.get(i).getPrice());
            map.put("img", R.mipmap.ic_launcher);
            list.add(map);
        }

        return list;
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

            SimpleAdapter adapter = new SimpleAdapter(ProductShowActivity.this,getData(),R.layout.v_list,
                    new String[]{"title","info","img"},
                    new int[]{R.id.title,R.id.info,R.id.img});
            lv.setAdapter(adapter);
        }
    };
}
