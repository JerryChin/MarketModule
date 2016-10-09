package com.hc.library.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.hc.library.R;
import com.hc.library.widget.recyclerview.decoration.HorizontalDividerItemDecoration;
import com.hc.library.widget.recyclerview.itemanimation.SlideInOutLeftItemAnimator;

import java.util.ArrayList;

/**
 * Created by fs on 2016/3/11.
 */
public class ObservableRecyclerView extends RecyclerView {
    private ArrayList<OnScrollChangeCompatListener> mOnScrollChangeListeners;

    public ObservableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        SlideInOutLeftItemAnimator animator = new SlideInOutLeftItemAnimator(this);
        HorizontalDividerItemDecoration itemDecoration = new HorizontalDividerItemDecoration.Builder(getContext()).build();

        this.setLayoutManager(manager);
        this.setItemAnimator(animator);
        this.addItemDecoration(itemDecoration);

    }

    public ObservableRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.RecyclerView);
    }

    public ObservableRecyclerView(Context context) {
        this(context, null);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mOnScrollChangeListeners != null) {
            for (OnScrollChangeCompatListener scrollChangeListener : mOnScrollChangeListeners) {
                scrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
            }
        }
    }

    public void addOnScrollChangeListener(OnScrollChangeCompatListener listener) {
        if (mOnScrollChangeListeners == null) {
            mOnScrollChangeListeners = new ArrayList<>();
        }

        mOnScrollChangeListeners.add(listener);
    }

    public void removeOnScrollChangeListener(OnScrollChangeCompatListener listener) {
        if (mOnScrollChangeListeners != null) {
            mOnScrollChangeListeners.remove(listener);
        }
    }

    public void removeOnScrollChangeListener(int index) {
        if (mOnScrollChangeListeners != null) {
            mOnScrollChangeListeners.remove(index);
        }
    }

    public boolean isTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (layoutManager != null) {
            return this.getChildCount() == 0 || layoutManager.findFirstVisibleItemPosition() == 0 && layoutManager.findViewByPosition(0).getTop() == 0;
        }
        throw new RuntimeException("请在使用LinearLayoutManager时使用此方法");
    }

    public boolean isBottom() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (layoutManager != null) {
            int index = this.getAdapter().getItemCount() - 1;
            return this.getChildCount() == 0 || layoutManager.findLastCompletelyVisibleItemPosition() == index;
        }
        throw new RuntimeException("请在使用LinearLayoutManager时使用此方法");
    }
}
