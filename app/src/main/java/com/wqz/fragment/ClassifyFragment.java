package com.wqz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wqz.marketmodule.AboutActivity;
import com.wqz.marketmodule.ProductShowActivity;
import com.wqz.marketmodule.R;


public class ClassifyFragment extends BaseFragment
{
    RelativeLayout rlClassify1;
    RelativeLayout rlClassify2;
    RelativeLayout rlClassify3;
    RelativeLayout rlClassify4;
    RelativeLayout rlClassify5;
    RelativeLayout rlClassify6;
    RelativeLayout rlClassify7;
    RelativeLayout rlClassify8;
    RelativeLayout rlClassify9;
    RelativeLayout rlClassify10;
    RelativeLayout rlClassify11;
    RelativeLayout rlClassify12;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState,int flag)
    {
        view = inflater.inflate(R.layout.fragment_classify, container, false);

        rlClassify1 = (RelativeLayout)view.findViewById(R.id.rl_classify1);
        rlClassify2 = (RelativeLayout)view.findViewById(R.id.rl_classify2);
        rlClassify3 = (RelativeLayout)view.findViewById(R.id.rl_classify3);
        rlClassify4 = (RelativeLayout)view.findViewById(R.id.rl_classify4);
        rlClassify5 = (RelativeLayout)view.findViewById(R.id.rl_classify5);
        rlClassify6 = (RelativeLayout)view.findViewById(R.id.rl_classify6);
        rlClassify7 = (RelativeLayout)view.findViewById(R.id.rl_classify7);
        rlClassify8 = (RelativeLayout)view.findViewById(R.id.rl_classify8);
        rlClassify9 = (RelativeLayout)view.findViewById(R.id.rl_classify9);
        rlClassify10 = (RelativeLayout)view.findViewById(R.id.rl_classify10);
        rlClassify11 = (RelativeLayout)view.findViewById(R.id.rl_classify11);
        rlClassify12 = (RelativeLayout)view.findViewById(R.id.rl_classify12);

        rlClassify1.setOnClickListener(l);
        rlClassify2.setOnClickListener(l);
        rlClassify3.setOnClickListener(l);
        rlClassify4.setOnClickListener(l);
        rlClassify5.setOnClickListener(l);
        rlClassify6.setOnClickListener(l);
        rlClassify7.setOnClickListener(l);
        rlClassify8.setOnClickListener(l);
        rlClassify9.setOnClickListener(l);
        rlClassify10.setOnClickListener(l);
        rlClassify11.setOnClickListener(l);
        rlClassify12.setOnClickListener(l);

        return view;
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ClassifyFragment.this.getActivity(), ProductShowActivity.class));
        }
    };
}
