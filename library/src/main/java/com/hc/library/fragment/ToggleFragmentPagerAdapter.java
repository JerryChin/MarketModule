package com.hc.library.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.fragment.FragmentToggle;
import com.hc.library.widget.CurrentPage;

/**
 * Created by xc on 2016/9/6.
 */
public abstract class ToggleFragmentPagerAdapter extends FragmentPagerAdapter implements CurrentPage{
    private CurrentFragment mCurrentFragment;

    public ToggleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mCurrentFragment = new CurrentFragment();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment.setPrimaryItem(container,position,object);
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Object getCurrentPage() {
        return mCurrentFragment.getCurrentPage();
    }
}
