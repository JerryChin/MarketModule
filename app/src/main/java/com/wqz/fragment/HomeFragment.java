package com.wqz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import android.widget.EditText;
import com.hc.library.widget.SlidingSwitcherView;
import com.wqz.app.MarketAPP;
import com.wqz.marketmodule.MainActivity;
import com.wqz.marketmodule.ProductShowActivity;
import com.wqz.marketmodule.R;

/**
 * 首页
 * @author Wqz
 * */
public class HomeFragment extends BaseFragment
{
    SlidingSwitcherView ssv;
    View view;
    RelativeLayout rlTime;
    RelativeLayout rlSales;
    RelativeLayout rlNew;
    RelativeLayout rlHot;
    RelativeLayout rlComment;
    Button btnSerach;
    EditText etSerach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, int flag) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();

        return view;
    }

    void initView()
    {
        ssv = (SlidingSwitcherView)view.findViewById(R.id.sliding_switcher_view);
        ssv.setImages(((MarketAPP)((MainActivity)getActivity()).getBaseApp()).getCarousel());

        rlTime = (RelativeLayout)view.findViewById(R.id.rl_time);
        rlSales = (RelativeLayout)view.findViewById(R.id.rl_sale_report);
        rlNew = (RelativeLayout)view.findViewById(R.id.rl_new);
        rlHot = (RelativeLayout)view.findViewById(R.id.rl_single);
        rlComment = (RelativeLayout)view.findViewById(R.id.rl_recommend);
        btnSerach = (Button)view.findViewById(R.id.btn_serach);
        etSerach = (EditText)view.findViewById(R.id.et_serach);

        rlTime.setOnClickListener(l);
        rlSales.setOnClickListener(l);
        rlNew.setOnClickListener(l);
        rlHot.setOnClickListener(l);
        rlComment.setOnClickListener(l);
        btnSerach.setOnClickListener(l);
    }

    View.OnClickListener l = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.rl_time:
                    break;
                case R.id.rl_sale_report:
                    break;
                case R.id.rl_new:
                    break;
                case R.id.rl_single:
                    break;
                case R.id.rl_recommend:
                    break;
                case R.id.btn_serach:
                    Bundle bundle = new Bundle();
                    bundle.putString("serach",etSerach.getText().toString());
                    bundle.putBoolean("isSerach",true);

                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(HomeFragment.this.getActivity(), ProductShowActivity.class);

                    startActivity(intent);
                    break;
            }
        }
    };
}
