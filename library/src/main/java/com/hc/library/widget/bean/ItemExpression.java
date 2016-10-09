package com.hc.library.widget.bean;

import android.content.Context;

/**
 * Created by xc on 2016/8/17.
 */
public class ItemExpression extends DrawableBean{
    private int groupId;

    public ItemExpression(Context context,int groupId) {
        super(context);
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

}
