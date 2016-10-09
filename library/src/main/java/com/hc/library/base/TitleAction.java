package com.hc.library.base;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;


public class TitleAction implements com.hc.library.util.TitleAction<TitleAction> {
	public final static int NONE = -1;

	int mPosition = NONE;
	private Context mContext;
	String mText;
	int mTextColor = NONE;
	Drawable mIcon;
	OnActionListener mActionListener;
	View mView;

	public TitleAction(Context context){
		mContext = context;
	}

	public Context getContext(){
		return mContext;
	}

	public TitleAction setText(@StringRes int resId){
		mText = mContext.getString(resId);

		return this;
	}

	@Override
	public TitleAction setText(String text){
		mText = text;

		return this;
	}

	@Override
	public TitleAction setTextColor(int color){
		mTextColor = color;

		return this;
	}

	@Override
	public TitleAction setTextColorResource(@ColorRes int colorRes){
		mTextColor = ContextCompat.getColor(mContext, colorRes);

		return this;
	}

	@Override
	public TitleAction setIcon(@DrawableRes int resId){
		mIcon = ContextCompat.getDrawable(mContext, resId);

		return this;
	}

	@Override
	public Drawable getIcon(){
		return mIcon;
	}

	@Override
	public TitleAction setIcon(Drawable drawable){
		mIcon = drawable;

		return this;
	}

	@Override
	public TitleAction setOnActionListener(OnActionListener l){
		mActionListener = l;

		return this;
	}

	@Override
	public com.hc.library.util.TitleAction.OnActionListener getActionListener(){
		return mActionListener;
	}

	@Override
	public String getText(){
		return mText;
	}

	@Override
	public TitleAction setActionView(View view) {
		mView = view;

		return this;
	}

	@Override
	public View getActionView(){
		return mView;
	}

	public TitleAction setPosition(int position){
		mPosition = position;

		return this;
	}


	@Override
	public int getPosition(){
		return mPosition;
	}

	@Override
	public int getTextColor(){
		return mTextColor;
	}

	@Override
	public void onDestroy(){
//		if(mIcon != null && mIcon instanceof BitmapDrawable){
//			BitmapDrawable bitmapDrawable = (BitmapDrawable)mIcon;
//			Bitmap bitmap = bitmapDrawable.getBitmap();
//			if(bitmap != null && !bitmap.isRecycled()){
//				bitmap.recycle();
//			}
//			
//			bitmap = null;
//			bitmapDrawable = null;
//			mContext = null;
//			
//			mIcon = null;
//		}

		//TODO 
	}
}
