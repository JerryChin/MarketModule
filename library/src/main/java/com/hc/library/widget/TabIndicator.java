package com.hc.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hc.library.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hc.library.util.ContextCompat.getDrawableArray;

/**
 * Created by Xiong on 2016/8/20.
 */
public class TabIndicator extends LinearLayout{
    private @LayoutRes int mItemLayoutId = R.layout.item_tabindicator;
    private @IdRes int mTextId = R.id.tv_tab_item;
    private @IdRes int mImageId = R.id.iv_tab_item;
    private int mTextColor = 0x88000000;
    private int mSelectedTextColor = 0x88000088;
    private List<Drawable> mDrawableArray;
    private List<Drawable> mSelectedDrawableArray;
    private List<CharSequence> mTextArray;
    private int mCount;
    private int mSelectedIndex = 0;
    private ViewPager mViewPager;

    private OnTabSelectedListener mListener;

    public TabIndicator(Context context) {
        this(context,null);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,R.attr.TabIndicatorStyle);
    }

    public TabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(!isInEditMode()) {
            initAttrs(context, attrs, defStyleAttr);
            initView();
        }
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicator);

        mItemLayoutId = a.getResourceId(R.styleable.TabIndicator_itemLayout,mItemLayoutId);
        mTextId = a.getResourceId(R.styleable.TabIndicator_textId,mTextId);
        mImageId = a.getResourceId(R.styleable.TabIndicator_imageId,mImageId);
        mTextColor = a.getColor(R.styleable.TabIndicator_android_textColor,mTextColor);
        mSelectedTextColor = a.getColor(R.styleable.TabIndicator_selectedTextColor,mSelectedTextColor);
        CharSequence[] textArray = a.getTextArray(R.styleable.TabIndicator_textArray);
        if(textArray != null && textArray.length > 0) {
            mTextArray = new ArrayList<>(Arrays.asList(textArray));
        }
        int drawableArrayId = a.getResourceId(R.styleable.TabIndicator_drawableArray,-1);
        int selectedDrawableArrayId = a.getResourceId(R.styleable.TabIndicator_selectedDrawableArray,-1);

        a.recycle();

        initDrawable(drawableArrayId,selectedDrawableArrayId);

    }

    private void initDrawable(int drawableArrayId,int selectedDrawableArrayId){
        if(!(drawableArrayId == -1 || selectedDrawableArrayId == -1)) {
            mDrawableArray = new ArrayList<>(Arrays.asList(getDrawableArray(getContext()
                    ,drawableArrayId)));
            mSelectedDrawableArray = new ArrayList<>(Arrays.asList(getDrawableArray(getContext()
                    ,selectedDrawableArrayId)));
        }
    }

    private void initView() {
        removeAllViews();
        if(mDrawableArray != null && mSelectedDrawableArray != null){
            mCount = mDrawableArray.size();

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;

            for(int i = 0;i < mCount;i++){
                View view = LayoutInflater.from(getContext()).inflate(mItemLayoutId,null,false);

                ViewHolder viewHolder = new ViewHolder();
                ImageView iv = (ImageView) view.findViewById(mImageId);
                TextView tv = (TextView) view.findViewById(mTextId);
                View redDot = view.findViewById(R.id.view_red_dot);
                viewHolder.mIv = iv;
                viewHolder.mTv = tv;
                viewHolder.mRedDot = redDot;

                view.setTag(viewHolder);

                if(i < mTextArray.size()) {
                    tv.setText(mTextArray.get(i));
                }
                if(i == mSelectedIndex){
                    setViewHolderState(viewHolder,mSelectedTextColor,mSelectedDrawableArray.get(i));
                }else{
                    setViewHolderState(viewHolder,mTextColor,mDrawableArray.get(i));
                }

                final int index = i;
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setSelection(index);
                    }
                });

                this.addView(view,params);
            }
        }
    }

    private void setViewHolderState(ViewHolder viewHolder,int color,Drawable drawable){
        viewHolder.mTv.setTextColor(color);
        viewHolder.mIv.setImageDrawable(drawable);
    }

    public void setSelection(int selectedIndex){
        if(selectedIndex == mSelectedIndex){
            return;
        }

        int oldSelectedIndex = mSelectedIndex;
        mSelectedIndex = selectedIndex;


        ViewHolder holder = (ViewHolder) getChildAt(oldSelectedIndex).getTag();
        setViewHolderState(holder,mTextColor,mDrawableArray.get(oldSelectedIndex));

        View itemView = getChildAt(selectedIndex);
        holder = (ViewHolder) itemView.getTag();
        setViewHolderState(holder,mSelectedTextColor,mSelectedDrawableArray.get(selectedIndex));


        if(mViewPager != null && mViewPager.getCurrentItem() != selectedIndex) {
            mViewPager.setCurrentItem(selectedIndex,false);
        }

        if(mListener != null){
            mListener.onSelected(this,itemView,selectedIndex);
        }
    }

    public void setRedDotVisibility(int position,boolean isVisibility){
        View view = getChildAt(position);
        if(view != null){
            ViewHolder holder = (ViewHolder) view.getTag();
            if(holder != null){
                holder.setRedDotVisibility(isVisibility);
            }
        }
    }

    public void setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        viewPager.setCurrentItem(mSelectedIndex);
        viewPager.addOnPageChangeListener(mOnPageChaneListener);
    }


    public void setOnTabSelectedListener(OnTabSelectedListener listener){
        mListener = listener;
    }

    public int getTabCount(){
        return mCount;
    }

    private final ViewPager.OnPageChangeListener mOnPageChaneListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setSelection(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    public interface OnTabSelectedListener{
        void onSelected(TabIndicator tab,View itemView,int position);
    }

    public class ViewHolder{
        private TextView mTv;
        private ImageView mIv;
        private View mRedDot;

        public void setRedDotVisibility(boolean isVisibility){
            if(mRedDot != null)
                mRedDot.setVisibility(isVisibility ? VISIBLE : GONE);
        }

        public View getRedDot(){
            return mRedDot;
        }

        public TextView getTextView(){
            return mTv;
        }

        public ImageView getImageView(){
            return mIv;
        }
    }
}
