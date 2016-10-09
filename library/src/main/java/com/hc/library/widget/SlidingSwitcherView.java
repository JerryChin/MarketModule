package com.hc.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hc.library.R;
import com.hc.library.util.ContextCompat;
import com.hc.library.util.DensityUtils;
import com.hc.library.util.L;
import com.hc.library.util.ViewCompat;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xc on 2016/8/23.
 */
public class SlidingSwitcherView extends FrameLayout {
    private InfiniteViewPager mViewPager;
    private DotListLayout mDotsContainer;
    private ArrayList<ImageView> mImageViews; // 滑动的图片集合

    private Drawable[] mImages; // 图片
    private ArrayList<View> mDots; // 图片标题正文的那些点

    //    private TextView mTvTitle;
    private volatile int mCurrentItem = 0; // 当前图片的索引号
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_XY;
    private boolean mIsScroll = false;
    private ScheduledExecutorService mTimer;
    private int mDotHeight;
    private int mDotWidth;
    private int mDotLeftMargin;
    private int mDotRightMargin;
    private int mDotListBottomMargin;
    private Drawable mDotDrawable;

    public SlidingSwitcherView(Context context) {
        this(context,null);
    }

    public SlidingSwitcherView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingSwitcherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDotListBottomMargin = (mDotLeftMargin = mDotRightMargin =
                (mDotHeight = mDotWidth = DensityUtils.dp2px(getContext(),6)) / 2) * 3;

        if(isInEditMode()){
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SlidingSwitcherView,defStyleAttr,
                0);
        int scaleType = a.getInt(R.styleable.SlidingSwitcherView_scaleType,-1);
        mScaleType = intToScaleType(scaleType);
        int resId = a.getResourceId(R.styleable.SlidingSwitcherView_imageArray,-1);
        mDotWidth = a.getDimensionPixelSize(R.styleable.SlidingSwitcherView_dotWidth,mDotWidth);
        mDotHeight = a.getDimensionPixelSize(R.styleable.SlidingSwitcherView_dotHeight,mDotHeight);
        mDotLeftMargin = a.getDimensionPixelSize(R.styleable.SlidingSwitcherView_dotLeftMargin,mDotLeftMargin);
        mDotRightMargin = a.getDimensionPixelSize(R.styleable.SlidingSwitcherView_dotRightMargin,mDotRightMargin);
        int dotResId = a.getResourceId(R.styleable.SlidingSwitcherView_dotBackground,R.drawable.selector_dot);

        a.recycle();

        if(resId != -1) {
            mImages = ContextCompat.getDrawableArray(getContext(), resId);
        }
        mDotDrawable = android.support.v4.content.ContextCompat.getDrawable(getContext(),dotResId);

        initView();
        mDotsContainer.setSlideViewBackground(mDotDrawable);
        initImageViews();
        initDots();

    }

    private ImageView.ScaleType intToScaleType(int type){
        switch (type){
            case 1:
                return ImageView.ScaleType.CENTER_CROP;
            case 2:
                return ImageView.ScaleType.CENTER;
            case 3:
                return ImageView.ScaleType.CENTER_INSIDE;
            case 4:
                return ImageView.ScaleType.FIT_START;
            case 5:
                return ImageView.ScaleType.FIT_CENTER;
            case 6:
                return ImageView.ScaleType.FIT_END;
            case 7:
                return ImageView.ScaleType.FIT_XY;
            case 8:
                return ImageView.ScaleType.MATRIX;
        }

        return mScaleType;
    }

    private void initView(){
        mViewPager = new InfiniteViewPager(getContext());
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int ev = event.getAction();

                if(mIsScroll) {
                    switch (ev) {
                        case MotionEvent.ACTION_DOWN:
                            stopScroll();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_OUTSIDE:
                            startScroll();
                            break;
                    }
                }

                return false;
            }
        });

        mDotsContainer = new DotListLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        params.bottomMargin = mDotListBottomMargin;

        addView(mViewPager);
        addView(mDotsContainer,params);
    }

    private void initImageViews() {
        if(getImageCount() == 0){
            return;
        }

        if(mImageViews == null){
            mImageViews = new ArrayList<>(getImageCount());
        }else{
            mImageViews.clear();
        }

        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        for(Drawable drawable:mImages){
            mImageViews.add(buildImageView(drawable,params));
        }

        mViewPager.setAdapter(new InfiniteViewPager.PagerAdapter(mImageViews) {
            @Override
            public View getFirstItem() {
                return buildImageView(mImages[0],params);
            }

            @Override
            public View getLastItem() {
                return buildImageView(mImages[mImages.length - 1],params);
            }

            @Override
            public int getCount() {
                return getViewList().size();
            }
        });
        mCurrentItem = 0;
        mViewPager.setCurrentItem(mCurrentItem);
    }

    private ImageView buildImageView(Drawable drawable,ViewGroup.LayoutParams params){
        ImageView iv =  new ImageView(getContext());
        iv.setScaleType(mScaleType);
        iv.setImageDrawable(drawable);
        iv.setLayoutParams(params);

        return iv;
    }

    private void initDots() {
        int len = getImageCount();

        if(mDots == null) {
            mDots = new ArrayList<>(len);
        }else{
            mDots.clear();
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotWidth,mDotHeight);
        params.leftMargin = mDotLeftMargin;
        params.rightMargin = mDotRightMargin;

        for(int i=0;i<len;i++){
            View view = new View(getContext());
            ViewCompat.setBackground(view,mDotDrawable.getConstantState().newDrawable());
            view.setSelected(mCurrentItem == i);
            android.support.v4.view.ViewCompat.setElevation(view,2);
            view.setLayoutParams(params);
            mDots.add(view);
            mDotsContainer.addView(view);
        }
    }

    private void setDotCurrentItem(int position){
        if(mDots == null)return;
        mDots.get(mCurrentItem).setSelected(false);
        mCurrentItem = position;
        mDots.get(position).setSelected(true);
    }

    public int getImageCount(){
        return mImages == null ? 0: mImages.length;
    }


    public void setScaleType(ImageView.ScaleType type){
        mScaleType = type;

        if(mImageViews != null){
            for(ImageView view : mImageViews){
                view.setScaleType(mScaleType);
            }
        }
    }

    public void setImages(@ArrayRes int resId){
        setImages(ContextCompat.getDrawableArray(getContext(),resId));
    }

    public void setImages(Drawable[] drawables){
        mImages = drawables;

        initImageViews();
        initDots();
    }

    public void setCurrentItem(int currentItem){
        mViewPager.setCurrentItem(currentItem);
        setDotCurrentItem(currentItem);
    }

    public void startScroll(){
        if(mDots.size() > 0 && mTimer == null){
            mTimer = Executors.newSingleThreadScheduledExecutor();
            mTimer.scheduleAtFixedRate(new Runnable() {
                @Override
                public synchronized void run() {
                    int currentItem = mCurrentItem + 1;
                    if (currentItem >= getImageCount()) {
                        currentItem = 0;
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.arg1 = currentItem;
                    msg.sendToTarget();
                }
            }, 2, 3, TimeUnit.SECONDS);

            mIsScroll = true;
        }
    }

    public void stopScroll(){
        if(mTimer != null && !mTimer.isShutdown()){
            mTimer.shutdown();
        }
        mTimer = null;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentItem(savedState.currentItemPosition);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentItemPosition = mCurrentItem;
        return savedState;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position != mCurrentItem) {
                setDotCurrentItem(position);
            }

            int count = mViewPager.getPageCount();
            if(mDotsContainer != null && !(position == count - 1 && positionOffset > 0)
                    && !(position == 0 && positionOffset < 0)){
                mDots.get(position).setSelected(false);
                int margin = mDotLeftMargin + mDotRightMargin + mDotWidth;
                mDotsContainer.setSlideViewLocation((int)
                        ((position + positionOffset) * margin + mDotLeftMargin),0);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private static class SavedState extends BaseSavedState{
        private int currentItemPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }


        public SavedState(Parcel in) {
            super(in);
            currentItemPosition = in.readInt();
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(currentItemPosition);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

    private class DotListLayout extends FrameLayout{
        private LinearLayout mLinearLayout;
        private View mSlideView;
        private LayoutParams mSlideViewParams;


        public DotListLayout(Context context) {
            this(context,null);
        }

        public DotListLayout(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public DotListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            mLinearLayout = new LinearLayout(getContext());
            mLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            super.addView(mLinearLayout);
            mSlideView = new View(getContext());
            mSlideView.setSelected(true);
            mSlideViewParams = new LayoutParams(mDotWidth,mDotHeight);
            mSlideViewParams.gravity = Gravity.LEFT | Gravity.TOP;
            mSlideView.setLayoutParams(mSlideViewParams);

            super.addView(mSlideView);
        }

        public void setSlideViewBackground(Drawable drawable){
            ViewCompat.setBackground(mSlideView,drawable);
        }

        @Override
        public void addView(View child) {
            mLinearLayout.addView(child);
        }

        public void setSlideViewLocation(int x,int y){
            mSlideViewParams.leftMargin = x;
            mSlideViewParams.topMargin = y;
            mSlideView.setLayoutParams(mSlideViewParams);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setCurrentItem(msg.arg1);
            return false;
        }
    });
}
