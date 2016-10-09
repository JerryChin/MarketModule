package com.hc.library.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by xc on 2016/8/17.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    private SparseArray<View> mViews;

    public View getConvertView(){
        return itemView;
    }

    public <V extends View> V getConvertView(Class<V> clazz){
        return clazz.cast(itemView);
    }

    public View getView(@IdRes int viewId){
        View view = mViews == null ? null : mViews.get(viewId);
        if(view != null){
            return view;
        }else{
            view = itemView.findViewById(viewId);
            if(view != null){
                if(mViews == null){
                    mViews = new SparseArray<>();
                }

                mViews.append(viewId, view);
            }

            return view;
        }

    }

    public <V extends View> V getView(@IdRes int viewId,Class<V> clazz){
        View view = getView(viewId);
        return view == null ? null : clazz.cast(view);
    }
}
