package com.hc.library.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiong on 2016/8/20.
 */
public class SimplePagerAdapter extends PagerAdapter {
    private List<? extends View> mViewList;

    public SimplePagerAdapter(){
        mViewList = new ArrayList<>();
    }

    public SimplePagerAdapter(@NonNull List<? extends View> views){
        mViewList = views;
    }

    public List<? extends View> getViewList(){
        return mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
