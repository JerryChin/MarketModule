package com.wqz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqz.marketmodule.R;

/**
 * 首页
 * @author Wqz
 * */
public class HomeFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, int flag) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        return view;
    }
}
