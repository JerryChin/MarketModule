package com.hc.library.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hc.library.util.ItemList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xc on 2016/8/17.
 */
public abstract class BaseRecyclerViewAdapter<D,VH extends ViewHolder> extends RecyclerView.Adapter<VH> implements ItemList<D,BaseRecyclerViewAdapter>{
    private Context mContext;
    private List<D> mData;
    private LayoutInflater mInflater;

    public BaseRecyclerViewAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(Context context,List<D> data){
        this(context);
        mData = data;
    }

    public Context getContext(){
        return mContext;
    }

    public void setData(List<D> data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public D getItem(int position){
        return mData == null ? null : mData.get(position);
    }

    @Override
    public BaseRecyclerViewAdapter addItem(D data) {
        if(mData.add(data)){
            this.notifyItemInserted(mData.indexOf(data));
        }
        return this;
    }

    @Override
    public BaseRecyclerViewAdapter addItem(int position, D item) {
        mData.add(position,item);
        this.notifyItemInserted(position);
        return this;
    }

    @Override
    public BaseRecyclerViewAdapter addItemAll(Collection<D> coll) {
        int start = mData.size();
        mData.addAll(coll);
        this.notifyItemRangeInserted(start,coll.size());
        return this;
    }

    @Override
    public BaseRecyclerViewAdapter addItemAll(int position, Collection<D> coll) {
        mData.addAll(position,coll);
        this.notifyItemRangeInserted(position,coll.size());
        return this;
    }

    @Override
    public D removeItem(int position) {
        D d = mData.remove(position);
        this.notifyItemRemoved(position);
        return d;
    }

    @Override
    public boolean removeItem(D item) {
        int index = mData.indexOf(item);
        if(mData.remove(item)){
            this.notifyItemRemoved(index);
            return true;
        }
        return false;
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(mInflater,parent,viewType);
    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);
}
