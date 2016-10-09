package com.hc.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hc.library.util.ItemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class BaseListViewAdapter<T> extends BaseAdapter implements ItemList<T,BaseListViewAdapter<T>> {
	private Context mContext;
	private List<T> mData;
	private Class<T> mClass;
	private LayoutInflater mInflater;
	
	public BaseListViewAdapter(Context context, Class<T> clazz){
		mContext = context;
		mClass = clazz;
		mInflater = LayoutInflater.from(context);
	}
	
	public BaseListViewAdapter(Context context, List<T> data, Class<T> clazz) {
		this(context,clazz);
		mData = data;
	}



	public void setData(List<T> data){
		mData = data;
		notifyDataSetChanged();
	}
	
	public void setData(Collection<T> data){
		mData = new ArrayList<>(data);
		notifyDataSetChanged();
	}
	
	public void setData(T[] data){
		mData = Arrays.asList(data);
		notifyDataSetChanged();
	}
	
	public Context getContext(){
		return mContext;
	}
	
	@Override
	public int getCount() {
		if(mData == null){
			return 0;
		}

		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData == null ? null : mData.get(position);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}
	

	@Override
	public boolean removeItem(T item){
		boolean b =  innerGetData().remove(item);
		if(b)notifyDataSetChanged();
		
		return b;
	}

	@Override
	public T removeItem(int position) {
		T data = innerGetData().remove(position);
		if(data != null)notifyDataSetChanged();
		return data;
	}

	@Override
	public BaseListViewAdapter<T> addItem(T item){
		if(innerGetData().add(item))notifyDataSetChanged();
		return this;
	}

	@Override
	public BaseListViewAdapter<T> addItem(int position, T item) {
		innerGetData().add(position,item);notifyDataSetChanged();
		return this;
	}

	@Override
	public BaseListViewAdapter<T> addItemAll(Collection<T> coll) {
		if(innerGetData().addAll(coll))notifyDataSetChanged();
		return this;
	}

	@Override
	public BaseListViewAdapter<T> addItemAll(int position, Collection<T> coll) {
		if(innerGetData().addAll(position,coll))notifyDataSetChanged();
		return this;
	}


	private List<T> innerGetData(){
		if(mData == null){
			mData = new ArrayList<>();
		}

		return mData;
	}


	public abstract View getConvertView(int viewType,LayoutInflater inflater, ViewGroup parent);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
		if(convertView != null){
			holder = (ViewHolder) convertView.getTag();
		}else{
			convertView = getConvertView(getItemViewType(position),mInflater,parent);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		

		T data = mClass.cast(getItem(position));
		bindView(position,data, holder);
		
		return convertView;
	}

	public abstract void bindView(int position,T data,ViewHolder viewHolder);
}
