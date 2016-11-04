package com.wqz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hc.library.base.BaseActivity;
import com.hc.library.base.BaseFragmentActivity;
import com.hc.library.pojo.User;
import com.hc.library.view.CircleImageView;
import com.squareup.picasso.Picasso;
import com.wqz.app.MarketAPP;
import com.wqz.marketmodule.AboutActivity;
import com.wqz.marketmodule.LoginActivity;
import com.wqz.marketmodule.R;
import com.wqz.marketmodule.SettingActivity;


/**
 * Created by Administrator on 2016/8/4.
 */
public class MeFragment extends BaseFragment
{
    RelativeLayout rlAbout;
    RelativeLayout rlSetting;
    CircleImageView headView;
    TextView tvNickname;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState,int flag)
    {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        initUI(view);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(user != null && user.getIcon() != null)
            Picasso.with(getActivity()).load(user.getIcon()).into(headView);

        if(user != null && user.getNickname() != null)
            tvNickname.setText(user.getNickname());
    }

    void initUI(View view)
    {
        user = ((BaseFragmentActivity)getActivity()).getBaseApp().getUser();
        rlAbout = (RelativeLayout)view.findViewById(R.id.rl_about);
        rlAbout.setOnClickListener(l);

        rlSetting = (RelativeLayout)view.findViewById(R.id.rl_setting);
        rlSetting.setOnClickListener(l);

        headView = (CircleImageView)view.findViewById(R.id.civ_head);
        headView.setOnClickListener(l);

        tvNickname = (TextView)view.findViewById(R.id.tv_nickname);
    }

    View.OnClickListener l = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.rl_about:
                    startActivity(new Intent(MeFragment.this.getActivity(), AboutActivity.class));
                    break;

                case R.id.rl_setting:
                    startActivity(new Intent(MeFragment.this.getActivity(), SettingActivity.class));
                    break;

                case R.id.civ_head:
                    if(user == null || user.getAccount() == null)
                        startActivity(new Intent(MeFragment.this.getActivity(), LoginActivity.class));
                    break;
            }
        }
    };
}
