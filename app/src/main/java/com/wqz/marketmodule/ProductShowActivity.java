package com.wqz.marketmodule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductShowActivity extends Activity
{

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);

        lv = (ListView)findViewById(R.id.listView1);

        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.v_list,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.img});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(listener);
    }

    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i = 0;i < 10;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "G " + i);
            map.put("info", "google " + i);
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
}
