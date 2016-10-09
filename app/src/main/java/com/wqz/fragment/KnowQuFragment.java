package com.wqz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqz.marketmodule.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class KnowQuFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState,int flag) {
        return inflater.inflate(R.layout.fragment_know_qu, container, false);
    }
}
