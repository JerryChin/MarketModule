package com.hc.library.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.StyleableRes;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hc.library.R;
import com.hc.library.base.TitleAction;
import com.hc.library.util.DensityUtils;
import com.hc.library.util.DrawableCompat;
import com.hc.library.util.ViewCompat;

/**
 * Created by Xiong on 2016/9/4.
 */
public class TitleBar extends LinearLayout {
    private @StyleableRes final static int ATTRS_COLORPRIMARY = 0;
    private final static int[] ATTRS;

    private View mFlBack,mBtnBack;
    private TextView mTvTitle;
    private LinearLayout mLlActionContainer;
    //显示网络状态
    private LinearLayout mLlNetworkState;
    private RelativeLayout mRlServerTitleBar;
    private Activity mActivity;

    static {
        ATTRS = new int[1];
        ATTRS[ATTRS_COLORPRIMARY] = android.support.v7.appcompat.R.attr.colorPrimary;
    }

    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,ATTRS,defStyleAttr,0);

        setBackgroundColor(a.getColor(ATTRS_COLORPRIMARY, Color.WHITE));

        a.recycle();

        setOrientation(VERTICAL);

        initView();

    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mRlServerTitleBar = (RelativeLayout) inflater.inflate(R.layout.layout_title,this,false);
        addView(mRlServerTitleBar);
        mFlBack = mRlServerTitleBar.findViewById(R.id.ll_back);
        mFlBack.setOnClickListener(mOnClickListener);
        mBtnBack=mFlBack.findViewById(R.id.bt_back);
        mTvTitle = (TextView) mRlServerTitleBar.findViewById(R.id.tv_title);
        if(getActivity() != null) {
            mTvTitle.setText(mActivity.getTitle());
        }
        mLlActionContainer = (LinearLayout) mRlServerTitleBar.findViewById(R.id.ll_action_contaner);


        mLlNetworkState = (LinearLayout) inflater.inflate(R.layout.layout_network_state,this,false);
        addView(mLlNetworkState);
        //设置右边箭头的background
        View rightArrowView = mLlNetworkState.findViewById(R.id.view_right_arrow);
        //给箭头着色
        ViewCompat.setBackground(rightArrowView, DrawableCompat.getTint(getContext(),R.drawable.ic_right_arrow_gray,
                R.color.colorGray));
        mLlNetworkState.setOnClickListener(mOnClickListener);
    }

    private Activity getActivity(){
        mActivity = mActivity == null ?
                getContext() instanceof Activity ?
                        (Activity) getContext() : null : mActivity;
        return mActivity;
    }

    public void setActivity(Activity activity){
        mActivity = activity;
    }

    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
    }

    public void setTitleTextColor(int color){
        mTvTitle.setTextColor(color);
    }

    public void setNetStateBarVisibility(int visibility){
        mLlNetworkState.setVisibility(visibility);
    }

    public void setBackButtonVisibility(int visibility){
        mFlBack.setVisibility(visibility);
    }

    public void setBackIconColor(int color){
        ViewCompat.setBackground(mBtnBack, DrawableCompat.getTint(mBtnBack.getBackground(),color));
    }

    public void addAction(final TitleAction action){
        final FrameLayout flAction = (FrameLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.layout_title_action, mLlActionContainer,false);

        Button btnAction = (Button) flAction.findViewById(R.id.btn_action);

        Drawable drawable = action.getIcon();
        if(drawable != null){
            ViewCompat.setBackground(btnAction,drawable);
        }else{
            int width = Math.round(StaticLayout.getDesiredWidth(action.getText(), btnAction.getPaint())) + DensityUtils.dp2px(getContext(), 24);
            flAction.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            ViewGroup.LayoutParams params = btnAction.getLayoutParams();
            params.width = params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            btnAction.setLayoutParams(params);

            btnAction.setText(action.getText());
        }

        action.setActionView(flAction);

        flAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((action.getActionListener() != null)){
                    int index =  mLlActionContainer.indexOfChild(v);
                    action.getActionListener().onAction(flAction,index);
                }
            }
        });

        if(action.getTextColor() != -1){
            btnAction.setTextColor(action.getTextColor());
        }

        if(action.getPosition() == -1){
            mLlActionContainer.addView(flAction);
        }else{
            mLlActionContainer.addView(flAction,action.getPosition());
        }
    }

    public void removeAction(TitleAction action){
        View view = action.getActionView();
        if(view != null) {
            mLlActionContainer.removeView(view);
        }
    }

    public void addCustomView(View customView){
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRlServerTitleBar.addView(customView,params);
    }

    public void addCustomView(View customView, ViewGroup.LayoutParams params){
        mRlServerTitleBar.addView(customView,params);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getActivity() != null) {
                int i = v.getId();
                if (i == R.id.ll_back) {
                    mActivity.onBackPressed();
                } else if (i == R.id.ll_network_state) {//跳转到设置页面
                    mActivity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            }
        }
    };
}
