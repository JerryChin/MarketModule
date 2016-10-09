package com.hc.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hc.library.R;
import com.hc.library.util.DensityUtils;
import com.hc.library.view.SideFilterBar;

/**
 * Created by Xiong on 2016/8/14.
 */
public class SideFilterLayout extends FrameLayout {
    private SideFilterBar mSideFilterBar;

    public SideFilterLayout(Context context) {
        this(context,null);
    }

    public SideFilterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SideFilterLayoutStyle);
    }

    public SideFilterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.SideFilterLayout);

        @IdRes
        int sideFilterBarId = ta.getResourceId(R.styleable.SideFilterLayout_SideFilterBarId,-1);

        ta.recycle();

        mSideFilterBar = new SideFilterBar(getContext());
        if(sideFilterBarId != -1){
            mSideFilterBar.setId(sideFilterBarId);
        }
        mSideFilterBar.setTextSize(DensityUtils.sp2px(getContext(),14));

        FrameLayout.LayoutParams params = new LayoutParams(DensityUtils.dp2px(getContext(),26)
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        mSideFilterBar.setLayoutParams(params);

        super.addView(mSideFilterBar);
        mSideFilterBar.setContainerLayout(this);
    }

    @Override
    public void addView(View child) {
        super.addView(child);filterBarBringToFront();
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);filterBarBringToFront();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);filterBarBringToFront();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);filterBarBringToFront();
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);filterBarBringToFront();
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        boolean b = super.addViewInLayout(child, index, params);filterBarBringToFront();
        return b;
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        boolean b = super.addViewInLayout(child, index, params, preventRequestLayout);filterBarBringToFront();
        return b;
    }


    private void filterBarBringToFront(){
        if(mSideFilterBar != null){
            mSideFilterBar.bringToFront();
        }
    }

    public SideFilterBar getSideFilterBar(){
        return mSideFilterBar;
    }
}
