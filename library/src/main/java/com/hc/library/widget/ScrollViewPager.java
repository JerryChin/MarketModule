package com.hc.library.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xc on 2016/8/22.
 */
public class ScrollViewPager extends ViewPager {
    private boolean mCanScroll = false;

    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mCanScroll && super.onTouchEvent(ev);
    }

    public boolean isCanScroll(){
        return mCanScroll;
    }

    public void setIsCanScroll(boolean isCanScroll){
        mCanScroll = isCanScroll;
    }
}
