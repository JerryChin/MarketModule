package com.hc.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hc.library.R;
import com.hc.library.util.DensityUtils;
import com.hc.library.util.ViewCompat;

import java.util.ArrayList;

/**
 * Created by Xiong on 2016/8/13.
 */
public class SideFilterBar extends View{
    private ArrayList<Character> mFilterList;
    private TextPaint mTextPaint;
    /**
     * 每个item的高度
     * */
    private int mItemHeight;
    /**
     * 字体的高度的一半
     * */
    private int mTextHeightHalf;
    private int mTopOffset;

    private char mCurrentFilterChar;

    private OnFilterChangeListener mListener;
    private FrameLayout mContainerLayout;
    private TextView mTvPopupChar;
    private Drawable mPopupBackground;
    private Drawable mBackground;
    private Drawable mPressedBackground;
    private int mPopupSize;
    private int mPopupTextSize;
    private int mPopupTextColor;

    public SideFilterBar(Context context) {
        this(context,null);
    }

    public SideFilterBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SideFilterBarStyle);
    }

    public SideFilterBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addFilterList();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        initAttrs(context,attrs,defStyleAttr);


        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = mFilterList.size();
        mItemHeight = height / size;
        mTopOffset = (height - mItemHeight * size) / 2;

        int width = Math.max(DensityUtils.dp2px(getContext(),25),mItemHeight);
        width = resolveSize(width,MeasureSpec.getSize(widthMeasureSpec));

        setMeasuredDimension(width,height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int size = mFilterList.size();
        int xOffset = getWidth() / 2;//x轴偏移量
        int yOffset = mItemHeight / 2 + mTextHeightHalf / 2 + mTopOffset;//y轴偏移量

        for(int i=0;i<size;i++) {
            canvas.drawText(mFilterList.get(i).toString(),xOffset,i * mItemHeight + yOffset,mTextPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int index = (int)(event.getY() - mTopOffset) / mItemHeight;
                if(index < 0 || index >= mFilterList.size()){
                    break;
                }
                char ch = mFilterList.get(index);
                if(action == MotionEvent.ACTION_DOWN){
                    mBackground = getBackground();
                    ViewCompat.setBackground(this,mPressedBackground);

                    filterChanged(ch);
                }else{
                    if(mCurrentFilterChar != ch){
                        filterChanged(ch);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                ViewCompat.setBackground(this,mBackground);

                if(mContainerLayout != null && mTvPopupChar != null){
                    mContainerLayout.removeView(mTvPopupChar);
                }

                break;
        }


        return super.onTouchEvent(event);
    }


    public void setOnFilterChangeListener(OnFilterChangeListener listener){
        mListener = listener;
    }


    private void addFilterList() {
        mFilterList = new ArrayList<>(27);
        for(char c = 'A';c <= 'Z';c++){
            mFilterList.add(c);
        }
        mFilterList.add('#');
    }

    private void filterChanged(char ch){
        if(mContainerLayout != null) {
            if (mTvPopupChar == null) {
                mTvPopupChar = new TextView(getContext());
                if(mPopupBackground != null) {
                    ViewCompat.setBackground(mTvPopupChar, mPopupBackground);
                }
                mTvPopupChar.setTextColor(mPopupTextColor);
                mTvPopupChar.setTextSize(mPopupTextSize);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mPopupSize, mPopupSize);
                params.gravity = Gravity.CENTER;
                mTvPopupChar.setGravity(Gravity.CENTER);
                mTvPopupChar.setLayoutParams(params);
            }

            mTvPopupChar.setText(String.valueOf(ch));
            if(mContainerLayout.indexOfChild(mTvPopupChar) == -1){
                mContainerLayout.addView(mTvPopupChar);
            }
        }
        mCurrentFilterChar = Character.toLowerCase(ch);
        if(mListener != null){
            mListener.onChange(mCurrentFilterChar);
        }
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.SideFilterBar,defStyleAttr,R.style.SideFilterBarStyle);
        int textColor = ta.getColor(R.styleable.PagerSlidingTabStrip_android_textColor, Color.BLACK);
        int textSize = ta.getDimensionPixelSize(R.styleable.SideFilterBar_android_textSize,
                DensityUtils.dp2px(getContext(),14));
        mPopupBackground = ta.getDrawable(R.styleable.SideFilterBar_popupBackground);
        mPressedBackground = ta.getDrawable(R.styleable.SideFilterBar_pressedBackground);
        mPopupSize = ta.getDimensionPixelSize(R.styleable.SideFilterBar_popupTextSize,DensityUtils.dp2px(getContext(),80));
        mPopupTextSize = DensityUtils.px2sp(getContext(),ta.getDimensionPixelSize(R.styleable.SideFilterBar_popupTextSize,0));
        if(mPopupTextSize == 0){
            mPopupTextSize = 50;
        }
        mPopupTextColor = ta.getColor(R.styleable.SideFilterBar_popupTextColor,Color.WHITE);

        ta.recycle();

        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);

        mTextHeightHalf = Math.round(mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent) / 2;
    }

    public void setContainerLayout(FrameLayout layout){
        mContainerLayout = layout;
    }

    public void addFilterChar(char filter){
        mFilterList.add(filter);
        requestLayout();
    }

    public void addFilterChar(int index,char filter){
        mFilterList.add(index,filter);
        requestLayout();
    }

    public void setTextColor(int color){
        mTextPaint.setColor(color);
        invalidate();
    }


    public void setTextSize(int size){
        mTextPaint.setTextSize(size);
        requestLayout();
    }

    public void setPopupSize(int dpSize){
        mPopupSize = dpSize;
        if(mTvPopupChar != null){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mTvPopupChar.getLayoutParams();
            params.height = dpSize;
            params.width = dpSize;

            mTvPopupChar.setLayoutParams(params);
        }
    }

    public void setPopupTextSize(int spSize){
        mPopupTextSize = spSize;
        if(mTvPopupChar != null){
            mTvPopupChar.setTextSize(spSize);
        }
    }

    public void setPopupTextColor(int color){
        mPopupTextColor = color;
        if(mTvPopupChar != null){
            mTvPopupChar.setTextColor(color);
        }
    }

    public void setPopupBackground(Drawable drawable){
        mPopupBackground = drawable;
        if(mTvPopupChar != null){
            ViewCompat.setBackground(mTvPopupChar,drawable);
        }
    }

    public void setPopupBackground(@DrawableRes int resId){
        Drawable drawable = ContextCompat.getDrawable(getContext(),resId);
        setPopupBackground(drawable);
    }

    public void setPressedBackground(Drawable drawable){
        mPressedBackground = drawable;
    }

    public void setPressedBackground(@DrawableRes int resId){
        Drawable drawable = ContextCompat.getDrawable(getContext(),resId);
        setPressedBackground(drawable);
    }

    public interface OnFilterChangeListener{
        void onChange(char filter);
    }
}
