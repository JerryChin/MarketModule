package com.hc.library.widget.bean;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.hc.library.bean.Id;

public class ItemPopupMenu extends Id{
	public final static int TYPE_BUTTON = 0;
	public final static int TYPE_LINE = 1;

	private int type = TYPE_BUTTON;
	private String text = null;
	private int textColor = -1;
	private int backColor = -1;
	private int lineBackColor = -1;
	
	private Context mContext;
	
	public ItemPopupMenu(Context context){
		mContext = context;
	}
	
	public int getType() {
		return type;
	}
	public ItemPopupMenu setType(int type) {
		this.type = type;
		
		return this;
	}
	public String getText() {
		return text;
	}
	public ItemPopupMenu setText(String text) {
		this.text = text;
		
		return this;
	}
	
	public ItemPopupMenu setText(@StringRes int resId){
		this.text = mContext.getString(resId);
		
		return this;
	}
	
	public int getTextColor() {
		return textColor;
	}
	public ItemPopupMenu setTextColor(int textColor) {
		this.textColor = textColor;
		
		return this;
	}
	
	public ItemPopupMenu setTextColorRes(@ColorRes int resId) {
		this.textColor = ContextCompat.getColor(mContext, resId);
		
		return this;
	}
	
	public int getBackColor() {
		return backColor;
	}
	public ItemPopupMenu setBackColor(int backColor) {
		this.backColor = backColor;
		
		return this;
	}
	
	
	public ItemPopupMenu setBackColorRes(@ColorRes int resId) {
		this.backColor = ContextCompat.getColor(mContext, resId);
		
		return this;
	}
	
	public int getLineBackColor() {
		return lineBackColor;
	}
	public ItemPopupMenu setLineBackColor(int lineBackColor) {
		this.lineBackColor = lineBackColor;
		
		return this;
	}
	
	public ItemPopupMenu setLineBackColorRes(@ColorRes int resId) {
		this.lineBackColor = ContextCompat.getColor(mContext, resId);
		
		return this;
	}
}
