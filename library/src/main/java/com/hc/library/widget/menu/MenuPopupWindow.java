package com.hc.library.widget.menu;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.hc.library.R;
import com.hc.library.adapter.BaseListViewAdapter;
import com.hc.library.adapter.ViewHolder;
import com.hc.library.widget.bean.ItemPopupMenu;
import com.hc.library.widget.popupWindow.ListPopupWindow;

import java.util.ArrayList;

public class MenuPopupWindow extends ListPopupWindow<ItemPopupMenu> {
	private BaseListViewAdapter<ItemPopupMenu> mAdapter;
	private @LayoutRes int mTitleLayout = R.layout.layout_popupwindow_title;
	private TextView mTitleView;


	public MenuPopupWindow(Activity app, ArrayList<ItemPopupMenu> data) {
		super(app, data);
		
		setWidth(LayoutParams.MATCH_PARENT);
		
		mAdapter = new BaseListViewAdapter<ItemPopupMenu>(app,ItemPopupMenu.class) {

			@Override
			public View getConvertView(int viewType,LayoutInflater inflater, ViewGroup parent) {
				View convertView = null;
				switch (viewType) {
				case ItemPopupMenu.TYPE_BUTTON:
					convertView = inflater.inflate(R.layout.item_simple_text
							, parent,false);
					break;
				case ItemPopupMenu.TYPE_LINE:
					convertView = inflater.inflate(R.layout.item_line_crude
							,parent,false);
					break;
				}
				
				
				return convertView;
			}

			@Override
			public void bindView(final int position, final ItemPopupMenu data, ViewHolder viewHolder) {
				int type = getItemViewType(position);
				switch (type) {
				case ItemPopupMenu.TYPE_BUTTON:
					final Button btn = viewHolder.getConvertView(Button.class);
					if(data.getText() != null){
						btn.setText(data.getText());
					}
					if(data.getTextColor() != -1){
						btn.setTextColor(data.getTextColor());
					}
					if(data.getBackColor() != -1){
						btn.setBackgroundColor(data.getBackColor());
					}
					
					btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							if(getOnItemClickListener() != null){
								getOnItemClickListener().onItemClick(getListView(),btn,position,data.getId());
								dismiss();
							}
						}
					});
					
					break;
				case ItemPopupMenu.TYPE_LINE:
					View line = viewHolder.getConvertView();
					if(data.getLineBackColor() != -1){
						line.setBackgroundColor(data.getLineBackColor());
					}
					break;
				}
			}


			@Override
			public int getItemViewType(int position) {
				ItemPopupMenu item = (ItemPopupMenu) getItem(position);
				return item.getType();
			}

			@Override
			public int getViewTypeCount() {
				return 2;
			}
		};
		
		setAdapter(mAdapter);
	}

	public BaseListViewAdapter<ItemPopupMenu> getAdapter(){
		return mAdapter;
	}
	
	public MenuPopupWindow(Activity app) {
		this(app,null);
	}

	public void setTitle(@StringRes int textRes){
		CharSequence text = getContext().getText(textRes);
		setTitle(text);
	}

	public void setTitle(CharSequence text){
		setTitle(text,-1);
	}

	public void setTitle(CharSequence text,@LayoutRes int layoutRes){
		int layoutId = layoutRes == -1 ? mTitleLayout : layoutRes;

		if(mTitleView == null) {
			View view = LayoutInflater.from(getActivity()).inflate(layoutId, getListView(), false);
			if (view instanceof TextView) {
				mTitleView = (TextView) view;
				mTitleView.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View view, MotionEvent motionEvent) {
						return true;
					}
				});
				getListView().setAdapter(null);
				getListView().addHeaderView(mTitleView);
				getListView().setAdapter(mAdapter);
			}else{
				throw new IllegalArgumentException("布局文件必须以textView为根");
			}
		}

		mTitleView.setText(text);
	}
}
