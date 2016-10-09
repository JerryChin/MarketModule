package com.hc.library.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xc on 2016/9/19.
 */
public interface FragmentLifeCycle extends LifeCycle{
    void onAttach(Context context);
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    void onActivityCreated(@Nullable Bundle savedInstanceState);
    void onDestroyView();
    void onDetach();
}
