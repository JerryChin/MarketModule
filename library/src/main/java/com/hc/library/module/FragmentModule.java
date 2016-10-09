package com.hc.library.module;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.library.base.BaseApplication;
import com.hc.library.fragment.BaseFragment;
import com.hc.library.util.FragmentLifeCycle;

/**
 * Created by xc on 2016/8/10.
 */
public class FragmentModule extends Module implements FragmentLifeCycle {
    private BaseFragment mFragment;
    private BaseApplication mBaseApp;

    public FragmentModule(BaseFragment fragment) {
        super(fragment.getContext());
        mFragment = fragment;
    }

    @Override
    public BaseApplication getBaseApp() {
        if (mBaseApp == null) {
            Application app = mFragment.getActivity().getApplication();
            if (app instanceof BaseApplication) {
                mBaseApp = (BaseApplication) app;
            }
        }
        return mBaseApp;
    }

    @Override
    public View findViewById(@IdRes int id) {
        View view = mFragment.getContentView();
        if (view.getId() == id && id > 0) {
            return view;
        } else {
            return view.findViewById(id);
        }
    }

    @Override
    public <V extends View> V findViewById(@IdRes int id, Class<V> viewType) {
        View view = findViewById(id);
        return view == null ? null : viewType.cast(view);
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onProInitView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onPostInitView() {

    }
}
