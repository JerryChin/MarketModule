package com.hc.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hc.library.util.CreateStateView;
import com.hc.library.util.IViewGroup;
import com.hc.library.widget.DefaultCreateStateViewImpl;

/**
 * Created by xc on 2016/9/7.
 */
public abstract class ToggleStateFragment extends BaseFragment implements CreateStateView{
    private CreateStateView mCreateStateView;
    private FrameLayout mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCreateStateView = new DefaultCreateStateViewImpl(getActivity(), this, new IViewGroup() {
            @Override
            public void addView(View view) {
                mContentView.addView(view);
            }

            @Override
            public void removeView(View view) {
                mContentView.removeView(view);
            }

            @Override
            public int indexOfChild(View view) {
                return mContentView.indexOfChild(view);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView == null){
            View view = onCreateView(inflater,container,savedInstanceState,0);
            if(view instanceof FrameLayout){
                mContentView = (FrameLayout) view;
            }else{
                mContentView = new FrameLayout(getActivity());
                mContentView.addView(view);
            }
        }

        return mContentView;
    }

    @Override
    public View onCreateErrorView() {
        return mCreateStateView.onCreateErrorView();
    }

    @Override
    public View onCreateEmptyView() {
        return mCreateStateView.onCreateEmptyView();
    }

    @Override
    public View onCreateLoadingView() {
        return mCreateStateView.onCreateLoadingView();
    }

    @Override
    public void showErrorView() {
        mCreateStateView.showErrorView();
    }

    @Override
    public void showEmptyView() {
        mCreateStateView.showEmptyView();
    }

    @Override
    public void showLoadingView() {
        mCreateStateView.showLoadingView();
    }

    @Override
    public void showContentView() {
        mCreateStateView.showContentView();
    }

    @Override
    public View getContentView() {
        return mContentView;
    }
}
