package com.hc.library.widget.popupWindow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hc.library.R;

public class BasePopupWindow extends PopupWindow{
	private boolean mShown = false;
	private boolean mIsSingle = true;

	private Activity mActivity;
	private Context mContext;
	private boolean mIsOverLayMask = true;
	
	public Activity getActivity(){
		return mActivity;
	}

	public Context getContext(){
		return mContext;
	}

	public BasePopupWindow(Activity activity){
		this(activity,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	public BasePopupWindow(Activity activity,int width,int height){
		super(activity);
		mActivity = activity;
		mContext = activity.getApplicationContext();

		setWidth(width);
		setHeight(height);

		this.setOutsideTouchable(true);
		this.setFocusable(true);
		this.setBackgroundDrawable(new ColorDrawable(0x00000000));
	}

	@Override
	public void dismiss() {
		super.dismiss();
		mShown = false;
		dismissForActivity();
	}


	@Override
	public void showAsDropDown(View anchor) {
		if(!checkShown()) {
			super.showAsDropDown(anchor);
			showForActivity();
		}
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		if(!checkShown()) {
			super.showAsDropDown(anchor, xoff, yoff);
			showForActivity();
		}
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
		if(!checkShown()) {
			if(Build.VERSION.SDK_INT < SUPPORT_VERSION) {
				super.showAsDropDown(anchor, xoff, getHeight() + yoff);
			}else{
				super.showAsDropDown(anchor,xoff,yoff,gravity);
			}
			showForActivity();
		}
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		if(!checkShown()) {
			super.showAtLocation(parent, gravity, x, y);
			showForActivity();
		}
	}

	private synchronized boolean checkShown(){
		if(mShown){
			return mIsSingle;
		}else {
			mShown = true;
			return false;
		}
	}


	private ValueAnimator mMaskAnim;
	private View mMaskView;
	private ViewGroup mContentView;

	private int SUPPORT_VERSION = Build.VERSION_CODES.KITKAT;

	protected void showForActivity(){
		if(mIsOverLayMask) {
			initAnim();
			if(Build.VERSION.SDK_INT < SUPPORT_VERSION){
				mMaskAnim.removeAllListeners();
				mMaskAnim.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animator) {
						if(mContentView.indexOfChild(mMaskView) == -1) {
							mContentView.addView(mMaskView);
						}
					}

					@Override
					public void onAnimationEnd(Animator animator) {
					}

					@Override
					public void onAnimationCancel(Animator animator) {
					}
					@Override
					public void onAnimationRepeat(Animator animator) {
					}
				});
			}
			mMaskAnim.start();
		}
	}

	protected void dismissForActivity(){
		if(mIsOverLayMask ) {
			initAnim();
			if(Build.VERSION.SDK_INT < SUPPORT_VERSION){
				mMaskAnim.removeAllListeners();
				mMaskAnim.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animator) {
					}

					@Override
					public void onAnimationEnd(Animator animator) {
						mMaskAnim.addListener(new Animator.AnimatorListener() {
							@Override
							public void onAnimationStart(Animator animator) {
							}
							@Override
							public void onAnimationEnd(Animator animator) {
								mContentView.removeView(mMaskView);
								mMaskAnim.removeAllListeners();
							}
							@Override
							public void onAnimationCancel(Animator animator) {
							}
							@Override
							public void onAnimationRepeat(Animator animator) {

							}
						});
					}

					@Override
					public void onAnimationCancel(Animator animator) {

					}

					@Override
					public void onAnimationRepeat(Animator animator) {

					}
				});
			}
			mMaskAnim.reverse();
		}
	}

	public void show(){
		showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}

	private void initAnim(){
		if(mMaskAnim == null) {
			if(Build.VERSION.SDK_INT < SUPPORT_VERSION){
				if(mContentView == null){
					mContentView = (ViewGroup) mActivity.findViewById(android.R.id.content);
					mMaskView = new View(getContext());
					mMaskView.setBackgroundColor(Color.BLACK);
					mMaskView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT));
				}

				mMaskAnim = ObjectAnimator.ofFloat(mMaskView,"alpha",0,0.3f);
			}else{
				View decorView = mActivity.getWindow().getDecorView();
				mMaskAnim = ObjectAnimator.ofFloat(decorView,"alpha",1,0.7f);
			}

			mMaskAnim.setDuration(180);
		}
	}

	public void setSingle(boolean isSingle){
		mIsSingle = isSingle;
	}

	/**
	 * 是否添加覆盖层
	 * */
	public void setOverLayMask(boolean isOverLayMask){
		mIsOverLayMask = isOverLayMask;
	}
}
