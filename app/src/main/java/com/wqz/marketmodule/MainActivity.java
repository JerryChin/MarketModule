package com.wqz.marketmodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hc.library.base.BaseFragmentActivity;
import com.hc.library.widget.ScrollViewPager;
import com.hc.library.widget.TabIndicator;
import com.wqz.fragment.HomeFragment;
import com.wqz.fragment.CartFragment;
import com.wqz.fragment.ClassifyFragment;
import com.wqz.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity
{
    ViewPager viewPager;
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    TabIndicator tabDraws;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    void initUI()
    {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ClassifyFragment());
        fragmentList.add(new CartFragment());
        fragmentList.add(new MeFragment());

        viewPager = (ScrollViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragAdapter(getSupportFragmentManager(),fragmentList));


        tabDraws = (TabIndicator)findViewById(android.R.id.tabs);
        tabDraws.setViewPager(viewPager);
    }

    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            // TODO Auto-generated constructor stub
            mFragments=fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mFragments.size();
        }

    }
}
