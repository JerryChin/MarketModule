package com.hc.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xc on 2016/9/20.
 */
public abstract class CommonRecyclerViewAdapter<D> extends BaseRecyclerViewAdapter<D,ViewHolder>{
    public CommonRecyclerViewAdapter(Context context) {
        super(context);
    }

    public CommonRecyclerViewAdapter(Context context, List<D> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(getConvertView(inflater,parent,viewType));
    }

    public abstract View getConvertView(LayoutInflater inflater, ViewGroup parent, int viewType);
}
