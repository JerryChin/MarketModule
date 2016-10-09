package com.hc.library.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.util.OnNetStateChangedListener;


/**
 * Created by xc on 2016/8/10.
 */
public abstract class BaseFragment extends Fragment{
    private View mContentView;

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * fragment是否创建完成
     * */
    private boolean mIsCreated = false;
    private OnCreatedFragmentListener mOnCreatedFragmentListener;

    public void setOnCreatedFragmentListener(OnCreatedFragmentListener listener){
        mOnCreatedFragmentListener = listener;
    }

    public boolean isCreated(){
        return mIsCreated;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsCreated = true;
        if(mOnCreatedFragmentListener != null){
            mOnCreatedFragmentListener.onCreated();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView == null) {
            mContentView = onCreateView(inflater,container,savedInstanceState,0);
        }

        return mContentView;
    }


    @Nullable
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState,
                             int flag);
    

    public View getContentView(){
        return mContentView;
    }


    public interface OnCreatedFragmentListener {
        void onCreated();
    }
}
