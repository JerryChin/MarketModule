package com.hc.library.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;

public class ViewCompat {
	private ViewCompat(){

	}

	public static void setBackground(View view,Drawable drawable){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
			view.setBackground(drawable);
		}else{
			view.setBackgroundDrawable(drawable);
		}
	}

	public static void setBackground(View view, @DrawableRes int resId){
		Drawable drawable = android.support.v4.content.ContextCompat.getDrawable(view.getContext(),resId);
		setBackground(view,drawable);
	}
}
