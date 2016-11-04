package com.wqz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hc.library.base.BaseActivity;
import com.hc.library.base.BaseFragmentActivity;
import com.hc.library.pojo.ProductEx;
import com.hc.library.pojo.User;
import com.wqz.app.MarketAPP;
import com.wqz.base.CartAdapter;
import com.wqz.marketmodule.R;
import com.wqz.marketmodule.SpalshActivity;
import com.wqz.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Wqz on 2016/8/4.
 */
public class CartFragment extends BaseFragment
{
    View view;
    ListView lCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState,int flag)
    {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initUI();
        return view;
    }

    void initUI()
    {
        lCart = (ListView)view.findViewById(R.id.lv_cart_list);


        MarketAPP thisApp = (MarketAPP)((BaseFragmentActivity)getActivity()).getBaseApp();
        if(thisApp != null && thisApp.getUser() != null && thisApp.getUser().getAccount() != null)
        {
            OkHttpUtils.get().url(Utils.SHOPPING_CART_GET)
                    .addParams("userid", ((MarketAPP)((BaseFragmentActivity)getActivity()).getBaseApp()).getUser().getId().toString())
                    .build().execute(cartCallback);
        }

    }

    StringCallback cartCallback = new StringCallback()
    {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(getActivity(),"Error Code : " + e.getMessage() ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String response, int id) {
            if(response.equals(""))
                Toast.makeText(getActivity(),"账号密码错误，请重新输入",Toast.LENGTH_LONG).show();
            else
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<ProductEx> pl = new ArrayList<ProductEx>();

                    for(int i = 0;i < jsonArray.length();i++)
                    {
                        pl.add(new Gson().fromJson(
                            jsonArray.get(i).toString(), ProductEx.class));
                    }
                    lCart.setAdapter(new CartAdapter(pl,CartFragment.this.getActivity()));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };
}
