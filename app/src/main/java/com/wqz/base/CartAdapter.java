package com.wqz.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hc.library.pojo.ProductEx;
import com.squareup.picasso.Picasso;
import com.wqz.marketmodule.R;

import java.util.ArrayList;

/**
 * Created by Wqz on 2016/11/4.
 */

public class CartAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private ArrayList<ProductEx> arrayList;
    private Context context;

    public CartAdapter(ArrayList<ProductEx> arrayList,Context context)
    {
        this.inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = inflater.inflate(R.layout.product_adapter, null);
        ImageView ivProduct = (ImageView)item.findViewById(R.id.iv_product);
        TextView tvProductName = (TextView)item.findViewById(R.id.tv_product_name);
        Picasso.with(context).load(arrayList.get(position).getImageaddress()).into(ivProduct);
        tvProductName.setText(arrayList.get(position).getName());

        return item;
    }
}
