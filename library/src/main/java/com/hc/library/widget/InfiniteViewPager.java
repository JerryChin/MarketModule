package com.hc.library.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.adapter.SimplePagerAdapter;

import java.util.List;

/**
 * Created by Xiong on 2016/8/23.
 */
public class InfiniteViewPager extends ViewPager {

    public InfiniteViewPager(Context context) {
        this(context,null);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(new InfinitePagerAdapter(adapter));
    }

    @Override
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        super.addOnPageChangeListener(new OnPageChangeListener(listener));
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        super.setOnPageChangeListener(new OnPageChangeListener(listener));
    }

    @Deprecated
    @Override
    public void setAdapter(android.support.v4.view.PagerAdapter adapter) {
        throw new RuntimeException("Deprecated");
    }

    @Override
    public int getCurrentItem() {
        return super.getCurrentItem() - 1;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int count = getAdapter().getCount();
        if(count > 1) {
            if(item < 0 || item > count - 3){
                throw new NumberFormatException("item:" + item);
            }
            int currentItem = getCurrentItem();
            if(currentItem == count - 3 && item == 0){
                super.setCurrentItem(count - 1);return;
            }else if(item == count - 3 && currentItem == 0){
                super.setCurrentItem(0);return;
            }
            super.setCurrentItem(item + 1,smoothScroll);
        }else{
            super.setCurrentItem(item,smoothScroll);
        }
    }

    public int getPageCount(){
        android.support.v4.view.PagerAdapter adapter = getAdapter();
        if(adapter == null){
            return 0;
        }else{
            int count = adapter.getCount();
            return count == 1 ? count : count - 2;
        }
    }

    private class OnPageChangeListener implements ViewPager.OnPageChangeListener {
        public ViewPager.OnPageChangeListener mBaseListener;

        public OnPageChangeListener(ViewPager.OnPageChangeListener listener){
            mBaseListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int count = getAdapter().getCount();
            if(count > 1) {
                if (position == 0) {
                    if(positionOffset == 0){
                        InfiniteViewPager.super.setCurrentItem(count - 2,false);
                    }else{
                        mBaseListener.onPageScrolled(position, -positionOffset, -positionOffsetPixels);
                    }
                } else if (position == count - 1) {
                    InfiniteViewPager.super.setCurrentItem(1,false);
                } else {
                    mBaseListener.onPageScrolled(position-1, positionOffset, positionOffsetPixels);
                }
            }else{
                mBaseListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            int count = getAdapter().getCount();
            if(count > 1) {
                if (position == 0) {

                } else if (position == count - 1) {

                } else {
                    mBaseListener.onPageSelected(position - 1);
                }
            }else{
                mBaseListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mBaseListener.onPageScrollStateChanged(state);
        }
    }

    public static abstract class PagerAdapter extends SimplePagerAdapter{
        public PagerAdapter(List<? extends View> views){
            super(views);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public abstract View getFirstItem();
        public abstract View getLastItem();
    }


    private static class InfinitePagerAdapter extends android.support.v4.view.PagerAdapter{
        private PagerAdapter mBaseAdapter;
        private View mFirstView;
        private View mLastView;

        private InfinitePagerAdapter(PagerAdapter adapter){
            setBasePagerAdapter(adapter);
        }

        private void setBasePagerAdapter(PagerAdapter adapter){
            mBaseAdapter = adapter;
            mFirstView = adapter.getFirstItem();
            mLastView = adapter.getLastItem();
        }


        @Override
        public int getCount() {
            int count = mBaseAdapter.getCount();
            //如果Page的数量大于1,返回值+2(放置第一个和最后一个view)
            return count < 1 ? count : count + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mBaseAdapter.isViewFromObject(view,object);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            mBaseAdapter.startUpdate(container);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(mBaseAdapter.getCount() > 1) {
                View view;
                if (position == 0) {
                    view = mLastView;
                } else if (position == getCount() - 1) {
                    view = mFirstView;
                } else {
                    return mBaseAdapter.instantiateItem(container, position - 1);
                }

                container.addView(view, 0);
                return view;
            }else {
                return mBaseAdapter.instantiateItem(container, position);
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(mBaseAdapter.getCount() > 1) {
                if (position == 0) {
                    container.removeView(mLastView);
                    return;
                } else if (position == getCount() - 1) {
                    container.removeView(mFirstView);
                    return;
                }
                mBaseAdapter.destroyItem(container, position - 1, object);
            }else{
                mBaseAdapter.destroyItem(container, position, object);
            }
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mBaseAdapter.setPrimaryItem(container,position,object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            mBaseAdapter.finishUpdate(container);
        }

        @Override
        public Parcelable saveState() {
            return mBaseAdapter.saveState();
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            mBaseAdapter.restoreState(state,loader);
        }

        @Override
        public int getItemPosition(Object object) {
            return mBaseAdapter.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mBaseAdapter.notifyDataSetChanged();
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            mBaseAdapter.registerDataSetObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            mBaseAdapter.registerDataSetObserver(observer);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mBaseAdapter.getPageTitle(position);
        }

        @Override
        public float getPageWidth(int position) {
            return mBaseAdapter.getPageWidth(position);
        }
    }
}
