package com.hc.library.widget.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hc.library.R;
import com.hc.library.adapter.BaseListViewAdapter;
import com.hc.library.adapter.ViewHolder;
import com.hc.library.util.DensityUtils;
import com.hc.library.util.ScreenUtils;
import com.hc.library.util.ViewCompat;
import com.hc.library.widget.popupWindow.BasePopupWindow;

import java.util.List;

/**
 * Created by xc on 2016/8/6.
 * 选项菜单
 */
public class OptionMenuPopupWindow extends BasePopupWindow {
    private final static int SUPPER_VERSION =  Build.VERSION_CODES.LOLLIPOP;
    private int[] ATTRS = {R.attr.colorPrimary};

    private ListView mListView;
    private BaseListViewAdapter<OptionMenuItem> mAdapter;
    private IOptionMenu mOptionMenu;
    private int mItemHeight;
    private int mMaxItemCount;
    private Drawable mBackground;
    private static int mWindowWidthOffset = Build.VERSION.SDK_INT >= SUPPER_VERSION ?
            0 : 20;

    public OptionMenuPopupWindow(Activity activity) {
        super(activity, (int) (ScreenUtils
                .getScreenWidth(activity.getApplicationContext()) * 0.6)
                + DensityUtils.dp2px(activity.getApplicationContext(),mWindowWidthOffset)
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        setOverLayMask(false);

        int height = ScreenUtils.getScreenHeight(getContext());
        mItemHeight = DensityUtils.dp2px(getContext(),48);
        mMaxItemCount = Math.round (height / 2f / mItemHeight);

        if(activity instanceof IOptionMenu){
            mOptionMenu = (IOptionMenu) activity;
        }

        setAnimationStyle(R.style.OptionMenuAnimStyle);

        TypedArray a = activity.getTheme().obtainStyledAttributes(ATTRS);
        mBackground = a.getDrawable(0);
        if(mBackground == null){
            mBackground = ContextCompat.getDrawable(getContext(),android.R.color.white);
        }

        a.recycle();

        initView();
    }

    private void initView() {
        mAdapter = new BaseListViewAdapter<OptionMenuItem>(getActivity(),
                OptionMenuItem.class) {
            @Override
            public View getConvertView(int viewType, LayoutInflater inflater,ViewGroup parent) {
                return inflater.inflate(R.layout.item_option_menu,parent,false);
            }

            @Override
            public void bindView(final int position, final OptionMenuItem data, ViewHolder viewHolder) {
                TextView textView = viewHolder.getView(R.id.tv_content, TextView.class);
                textView.setText(data.getText());
                if(data.getTextColor() != OptionMenuItem.NONE){
                    textView.setTextColor(data.getTextColor());
                }

                if(data.getIcon() != null){
                    viewHolder.getView(R.id.iv_icon, ImageView.class).setImageDrawable(data.getIcon());
                }


                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.getActionListener() != null) {
                            if(data.getActionListener().onAction(view, position)){
                                OptionMenuPopupWindow.this.dismiss();
                            }
                        }else if (mOptionMenu != null){
                            if (mOptionMenu.onOptionsItemSelected(position)) {
                                OptionMenuPopupWindow.this.dismiss();
                            }
                        }
                    }
                });
            }
        };

        if (Build.VERSION.SDK_INT >= SUPPER_VERSION) {
            mListView = new ListView(getActivity());
            setContentView(mListView);
            setBackgroundDrawable(mBackground);
            setElevation(DensityUtils.dp2px(getActivity().getApplicationContext(),6));
        }else{
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_option_menu,null,false);
            mListView = (ListView) view.findViewById(R.id.listView);
            ViewCompat.setBackground(mListView,mBackground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OptionMenuPopupWindow.this.dismiss();
                }
            });
            setContentView(view);
        }

        mListView.setAdapter(mAdapter);

        mListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //使activity可以在PopupWindow弹出的时候响应keyEvent
                return getActivity().getWindow().getDecorView().dispatchKeyEvent(keyEvent);
            }
        });
    }

    public void setData(List<OptionMenuItem> items){
        mAdapter.setData(items);

        int height;

        if(items.size() > mMaxItemCount){
            height = mMaxItemCount * mItemHeight;
            if(Build.VERSION.SDK_INT < SUPPER_VERSION){
                height+=DensityUtils.dp2px(getContext(),20);
            }
        }else{
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        this.setHeight(height);
    }

    public void notifyDataSetChanged(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= SUPPER_VERSION) {
            this.showAsDropDown(anchor
                    , -DensityUtils.dp2px(getContext(), 10)
                    , 0, Gravity.TOP | Gravity.RIGHT);
        }else{
            this.showAsDropDown(anchor
                    , -DensityUtils.dp2px(getContext(), 0)
                    , -DensityUtils.dp2px(getContext(), 10), Gravity.TOP | Gravity.RIGHT);
        }
    }

    @Override
    protected void showForActivity() {
        if(mOptionMenu != null){
            mOptionMenu.onMenuOpened();
        }
    }

    @Override
    protected void dismissForActivity() {
        if(mOptionMenu != null){
            mOptionMenu.onMenuOpened();
        }
    }
}
