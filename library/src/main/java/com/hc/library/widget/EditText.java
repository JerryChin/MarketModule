package com.hc.library.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hc.library.R;


public class EditText extends android.widget.EditText {
	private Drawable[] mDrawables;

	public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs,defStyleAttr);

		mDrawables = getCompoundDrawables();

		addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				textChanged();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});


		textChanged();
	}

	private void textChanged(){
		if(TextUtils.isEmpty(EditText.this.getText())){
			setCompoundDrawables(mDrawables[0],mDrawables[1],null, mDrawables[3]);
		}else{
			setCompoundDrawables(mDrawables[0],mDrawables[1],mDrawables[2],mDrawables[3]);
		}
	}
	
	public EditText(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.EditTextStyle);
	}

	public EditText(Context context) {
		this(context,null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = Math.round(event.getX());
		if(getWidth() - x < getHeight()){
			setText(null);
		}
		
		return super.onTouchEvent(event);
	}
	
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		mDrawables = null;

//		for(Drawable drawable : mDrawables){
//			if(drawable instanceof BitmapDrawable){
//				Bitmap bt = ((BitmapDrawable)drawable).getBitmap();
//				if(bt != null && !bt.isRecycled()){
//					bt.recycle();
//				}
//			}
//		}
	}
}
