package com.hc.library.widget.popupWindow;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hc.library.R;
import com.hc.library.adapter.BaseListViewAdapter;
import com.hc.library.util.DensityUtils;

import java.util.ArrayList;

public class ListPopupWindow<T> extends BasePopupWindow{
	private ListView mListView;
	private BaseListViewAdapter<T> mAdapter;
	private ArrayList<T> mData;
	private AdapterView.OnItemClickListener mListener;


	public ListPopupWindow(Activity app){
		this(app,null);
	}

	public ListPopupWindow(Activity app,ArrayList<T> data){
		super(app);

		mListView = new ListView(app);
		mListView.setDivider(new ColorDrawable(ContextCompat.getColor(app, R.color.colorMainBg)));
		mListView.setDividerHeight(DensityUtils.dp2px(app.getApplicationContext(), 1));
		mListView.setBackgroundResource(android.R.color.white);
		setContentView(mListView);

		setAnimationStyle(R.style.PopupAnimation);

		mData = data;
	}


	public ListView getListView(){
		return mListView;
	}

	public void setAdapter(@NonNull BaseListViewAdapter<T> adapter){
		if(mData != null){
			adapter.setData(mData);
		}

		mAdapter = adapter;
		mListView.setAdapter(adapter);
	}

	public void setData(@NonNull ArrayList<T> data){
		if(mAdapter != null){
			mAdapter.setData(data);
		}

		mData = data;
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
		mListener =listener;
	}
	
	protected AdapterView.OnItemClickListener getOnItemClickListener(){
		return mListener;
	}

}
