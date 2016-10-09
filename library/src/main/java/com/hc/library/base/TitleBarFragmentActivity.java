package com.hc.library.base;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hc.library.widget.menu.IOptionMenu;
import com.hc.library.widget.menu.OptionMenuTitleAction;
import com.hc.library.module.ActivityModule;
import com.hc.library.widget.TitleBar;


/**
 * Created by xc on 2016/8/6.
 */
public abstract class TitleBarFragmentActivity extends BaseFragmentActivity implements IOptionMenu {
    private TitleBarModule mTitleBarModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleBarModule = (TitleBarModule) getMainModule();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mTitleBarModule.onPostCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ActivityModule onCreateMainModular() {
        return new TitleBarModule(this,isClipView());
    }


    protected boolean isClipView(){
        return true;
    }

    /**
     * 设置contentView是否把超出view边界的视图裁剪掉
     * */
    public void setContentViewClip(boolean isClip){
        mTitleBarModule.setContentViewClip(isClip);
    }

    public void setTitleBarBackground(Drawable drawable){
        mTitleBarModule.setTitleBarBackground(drawable);
    }

    public void setTitleBarBackground(@DrawableRes int resId){
        mTitleBarModule.setTitleBarBackground(resId);
    }

    public void setTitleTextColor(int textColor) {
        mTitleBarModule.setTitleTextColor(textColor);
    }

    public void addAction(TitleAction action){
        mTitleBarModule.addAction(action);
    }

    public void removeAction(TitleAction action){
        mTitleBarModule.removeAction(action);
    }

    public void clearAction(){
        mTitleBarModule.clearAction();
    }

    public void setDisplayHomeAsUpEnabled(boolean enabled){
        mTitleBarModule.setDisplayHomeAsUpEnabled(enabled);
    }

    @Deprecated
    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onMenuOpened() {
    }

    @Override
    public void onOptionsMenuClosed() {
    }

    @Override
    public boolean onOptionsItemSelected(int index){
        return true;
    }

    @Deprecated
    @Override
    public final boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Deprecated
    @Override
    public final void onOptionsMenuClosed(Menu menu) {
        
    }

    @Deprecated
    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        mTitleBarModule.setTitle(title);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mTitleBarModule.onKeyUp(keyCode,event);
        return super.onKeyUp(keyCode, event);
    }

    public TitleBar getTitleBar(){
        return mTitleBarModule.getTitleBar();
    }


    public void addTopView(View view) {
        mTitleBarModule.addTopView(view);
    }

    public void removeTopView(View view) {
        mTitleBarModule.removeTopView(view);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setStateBarBackground(Drawable background){
        mTitleBarModule.setStateBarBackground(background);
    }

//    public void setShowNetStateBar(boolean isShow){
//        mTitleBarModule.setShowNetStateBar(isShow);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTitleBarModule.onDestroy();
    }
}
