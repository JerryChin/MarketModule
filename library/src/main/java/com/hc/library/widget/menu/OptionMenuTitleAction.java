package com.hc.library.widget.menu;

import android.app.Activity;
import android.view.View;


import com.hc.library.base.TitleAction;
import com.hc.library.util.ItemList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by xc on 2016/8/6.
 */
public class OptionMenuTitleAction extends TitleAction implements ItemList<OptionMenuItem,OptionMenuTitleAction> {
    private ArrayList<OptionMenuItem> mMenuItems;
    private OptionMenuPopupWindow mMenuPopupWindow;
    private Activity mActivity;

    public OptionMenuTitleAction(Activity activity) {
        super(activity.getApplicationContext());
        mActivity = activity;

        setText("菜单");

        mMenuItems = new ArrayList<>();

        super.setOnActionListener(new OnActionListener() {
            @Override
            public boolean onAction(View view, int position) {
                toggleMenu();

                return true;
            }
        });
    }

    @Deprecated
    @Override
    public TitleAction setOnActionListener(OnActionListener l) {
        throw new RuntimeException("该方法已被废弃");
    }

    @Override
    public OptionMenuTitleAction addItem(OptionMenuItem data) {
        mMenuItems.add(data);
        if(mMenuPopupWindow != null)mMenuPopupWindow.notifyDataSetChanged();
        return this;
    }

    @Override
    public OptionMenuTitleAction addItem(int position, OptionMenuItem item) {
        mMenuItems.add(position,item);
        if(mMenuPopupWindow != null)mMenuPopupWindow.notifyDataSetChanged();
        return this;
    }

    @Override
    public OptionMenuTitleAction addItemAll(Collection<OptionMenuItem> coll) {
        mMenuItems.addAll(coll);
        if(mMenuPopupWindow != null)mMenuPopupWindow.notifyDataSetChanged();
        return this;
    }

    @Override
    public OptionMenuTitleAction addItemAll(int position, Collection<OptionMenuItem> coll) {
        mMenuItems.addAll(position,coll);
        if(mMenuPopupWindow != null)mMenuPopupWindow.notifyDataSetChanged();
        return this;
    }

    @Override
    public OptionMenuItem removeItem(int position) {
        OptionMenuItem item = mMenuItems.remove(position);
        if(item != null && mMenuPopupWindow != null)mMenuPopupWindow.notifyDataSetChanged();
        return item;
    }

    @Override
    public boolean removeItem(OptionMenuItem item) {
        boolean b = mMenuItems.remove(item);
        if(b && mMenuPopupWindow != null)mMenuPopupWindow.notifyDataSetChanged();
        return b;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mActivity = null;
        mMenuItems = null;
        mMenuPopupWindow = null;
    }

    private void closeMenu(){
        mMenuPopupWindow.dismiss();
    }

    private void showMenu(){
        if(mMenuPopupWindow == null) {
            mMenuPopupWindow = new OptionMenuPopupWindow(mActivity);
            mMenuPopupWindow.setData(mMenuItems);
        }

        mMenuPopupWindow.showAsDropDown(getActionView());
    }

    public void toggleMenu(){
        if(mMenuPopupWindow != null && mMenuPopupWindow.isShowing()){
            closeMenu();
        }else{
            showMenu();
        }
    }
}
