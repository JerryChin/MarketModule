package com.hc.library.widget.menu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hc.library.R;
import com.hc.library.adapter.BaseListViewAdapter;
import com.hc.library.adapter.ViewHolder;
import com.hc.library.util.DensityUtils;
import com.hc.library.util.ItemList;
import com.hc.library.util.ScreenUtils;
import com.hc.library.util.TextItem;

import java.util.Collection;

/**
 * Created by xc on 2016/8/15.
 */
public class ContextMenuDialog extends Dialog implements ItemList<ContextMenuDialog.ContextMenuItem,ContextMenuDialog>{
    private ListView mListView;
    private BaseListViewAdapter<ContextMenuItem> mAdapter;

    public ContextMenuDialog(Activity activity, final AdapterView.OnItemClickListener itemClickListener) {
        super(activity, R.style.Dialog);

        mListView = new ListView(activity);
        int margin = DensityUtils.dp2px(getContext(),2);
        mListView.setPadding(0,margin,0,margin);
        mAdapter = new BaseListViewAdapter<ContextMenuItem>(getContext(),ContextMenuItem.class) {
            @Override
            public View getConvertView(int viewType,LayoutInflater inflater, ViewGroup parent) {
                return inflater.inflate(R.layout.item_context_menu,parent,false);
            }

            @Override
            public void bindView(final int position, final ContextMenuItem data, final ViewHolder viewHolder) {
                TextView tv = viewHolder.getConvertView(TextView.class);
                tv.setText(data.getText());
                if(data.getTextColor() != -1) {
                    tv.setTextColor(data.getTextColor());
                }
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(mListView,viewHolder.getConvertView(),position,data.getId());
                        dismiss();
                    }
                });
            }
        };

        mListView.setAdapter(mAdapter);
        mListView.setBackgroundResource(R.drawable.shape_arc_white);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                (int) (ScreenUtils.getScreenWidth(getContext()) * 0.8),ViewGroup.LayoutParams.WRAP_CONTENT);

        setContentView(mListView,params);
    }

    @Override
    public ContextMenuDialog addItem(ContextMenuItem data) {
        mAdapter.addItem(data);
        if(this.isShowing()){
            notifyDataSetChanged();
        }

        return this;
    }

    @Override
    public ContextMenuDialog addItem(int position, ContextMenuItem item) {
        mAdapter.addItem(position,item);
        if(this.isShowing()){
            notifyDataSetChanged();
        }

        return this;
    }

    @Override
    public ContextMenuDialog addItemAll(Collection<ContextMenuItem> coll) {
        mAdapter.addItemAll(coll);
        if(this.isShowing()){
            notifyDataSetChanged();
        }

        return this;
    }

    @Override
    public ContextMenuDialog addItemAll(int position, Collection<ContextMenuItem> coll) {
        mAdapter.addItemAll(position,coll);
        if(this.isShowing()){
            notifyDataSetChanged();
        }

        return this;
    }

    @Override
    public ContextMenuItem removeItem(int position) {
        ContextMenuItem item = mAdapter.removeItem(position);
        if(item != null && this.isShowing()){
            notifyDataSetChanged();
        }

        return item;
    }


    @Override
    public boolean removeItem(ContextMenuItem item) {
        boolean b = mAdapter.removeItem(item);
        if(b && this.isShowing()){
            notifyDataSetChanged();
        }

        return b;
    }

    private void notifyDataSetChanged(){
        fixedHeight();
        mAdapter.notifyDataSetChanged();
    }

    private void fixedHeight(){
        int itemHeight = DensityUtils.dp2px(getContext(),48);
        int maxCount = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.7 / itemHeight);
        if(mAdapter.getCount() > maxCount){
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = maxCount * itemHeight;
            mListView.setLayoutParams(params);
        }
    }

    @Override
    public void show() {
        fixedHeight();
        super.show();
    }

    public static class ContextMenuItem implements TextItem<ContextMenuItem>{
        private Context mContext;
        private int mId;
        private String mText;
        private int mTextColor = -1;

        public ContextMenuItem(Context context,int id) {
            mContext = context;
            mId = id;
        }

        @Override
        public ContextMenuItem setText(@StringRes int resId) {
            mText = mContext.getResources().getString(resId);
            return this;
        }

        @Override
        public ContextMenuItem setText(String text) {
            mText = text;
            return this;
        }

        @Override
        public ContextMenuItem setTextColor(int color) {
            mTextColor = color;
            return this;
        }

        @Override
        public ContextMenuItem setTextColorResource(@ColorRes int colorRes) {
            mTextColor = ContextCompat.getColor(mContext,colorRes);
            return this;
        }

        @Override
        public int getTextColor() {
            return mTextColor;
        }

        @Override
        public String getText() {
            return mText;
        }

        public int getId(){
            return mId;
        }
    }
}
