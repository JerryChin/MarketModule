package com.hc.library.widget;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by xc on 2016/8/29.
 */
public class PtrPagerHandler implements PtrHandler,ViewPager.OnPageChangeListener{
    private CurrentPage mCurrentPage;
    private PtrClassicFrameLayout mPtrLayout;
    private Object mCurrent;

    public PtrPagerHandler(@NonNull CurrentPage currentPage,PtrClassicFrameLayout layout){
        mPtrLayout = layout;
        mCurrentPage = currentPage;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        Object current = mCurrentPage.getCurrentPage();
        return current != null && current instanceof PtrHandler && ((PtrHandler) current).checkCanDoRefresh(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        mCurrent = mCurrentPage.getCurrentPage();
        if(mCurrent != null && mCurrent instanceof PtrHandler){
            ((PtrHandler)mCurrent).onRefreshBegin(frame);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(mPtrLayout.isRefreshing()){
            mPtrLayout.refreshComplete(PtrFrameLayout.COMPLETE_CODE_CANCEL);
            if(mCurrent instanceof Cancelable){
                ((Cancelable)mCurrent).onCancel();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
