package com.hc.library.widget.menu;

/**
 * Created by xc on 2016/8/8.
 */
public interface IOptionMenu {
    void onMenuOpened();
    void onOptionsMenuClosed();
    boolean onOptionsItemSelected(int index);
}
