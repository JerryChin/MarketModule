package com.hc.library.widget.bean;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hc.library.util.ItemList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xc on 2016/8/17.
 */
public class ItemExpressionGroup extends DrawableBean implements ItemList<ItemExpression,ItemExpressionGroup>{
    private int type;
    private List<ItemExpression> expressions;

    public ItemExpressionGroup(Context context) {
        super(context);
        expressions = new ArrayList<>();
    }

    public ItemExpressionGroup(Context context,@NonNull List<ItemExpression> expressions) {
        super(context);
        this.expressions = expressions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ItemExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ItemExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public ItemExpressionGroup addItem(ItemExpression data) {
        expressions.add(data);

        return this;
    }

    @Override
    public ItemExpressionGroup addItem(int position, ItemExpression item) {
        expressions.add(position,item);
        return this;
    }

    @Override
    public ItemExpressionGroup addItemAll(Collection<ItemExpression> coll) {
        expressions.addAll(coll);
        return this;
    }

    @Override
    public ItemExpressionGroup addItemAll(int position, Collection<ItemExpression> coll) {
        expressions.addAll(position,coll);
        return this;
    }

    @Override
    public ItemExpression removeItem(int position) {
        return expressions.remove(position);
    }

    @Override
    public boolean removeItem(ItemExpression item) {
        return expressions.remove(item);
    }


}
