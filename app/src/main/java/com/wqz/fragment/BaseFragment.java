package com.wqz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hc.library.widget.DefaultCreateStateViewImpl;
import com.hc.library.util.CreateStateView;
import com.hc.library.util.IViewGroup;
import com.hc.library.util.OnNetStateChangedListener;


/**
 * Created by xc on 2016/8/10.
 */
public abstract class BaseFragment extends Fragment implements OnNetStateChangedListener,CreateStateView {
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
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = new FrameLayout(getActivity());
        View view = onCreateView(inflater,mContentView,savedInstanceState,0);
        if(view != null) {
            mContentView.addView(view);
        }
        return mContentView;
    }

    public abstract View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState,int flag);

    @Override
    public void onNetStateChanged(int networkState) {
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
}
