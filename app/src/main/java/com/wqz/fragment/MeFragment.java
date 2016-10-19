package com.wqz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wqz.marketmodule.AboutActivity;
import com.wqz.marketmodule.R;


/**
 * Created by Administrator on 2016/8/4.
 */
public class MeFragment extends BaseFragment
{
    ImageButton ibSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState,int flag)
    {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ibSetting = (ImageButton)view.findViewById(R.id.ib_setting_activity);
        ibSetting.setOnClickListener(l);

        return view;
    }

    View.OnClickListener l = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ib_setting_activity:
                    startActivity(new Intent(MeFragment.this.getActivity(), AboutActivity.class));
                    break;
            }
        }
    };
}
