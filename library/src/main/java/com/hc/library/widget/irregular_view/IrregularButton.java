package com.hc.library.widget.irregular_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.hc.library.util.Compat;
import com.hc.library.util.DrawableUtils;

/**
 * Created by Wqz on 2016/8/29.
 */

public class IrregularButton extends View
{
    private Bitmap mBitmap;

    public interface TouchChecker
    {
        boolean isInTouchArea(int x, int y, int width, int height);
    }

    //核心接口：
    //在Button中实现不同的TouchChecker，以达到在Button得到点击事件后有不同的响应。
    //1.IgnoreTransparentTouchChecker为不响应透明底图的类型
    private TouchChecker touchChecker;

    public IrregularButton(Context context)
    {
        this(context,null);
    }
    public IrregularButton(Context context, AttributeSet attrs)
    {
        this(context, attrs,android.R.attr.buttonStyle);
    }
    public IrregularButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(touchChecker == null) {
                    mBitmap = DrawableUtils.viewToBitmap(IrregularButton.this);
                    touchChecker = new IgnoreTransparentTouchChecker(mBitmap);
                }
                Compat.removeOnGlobalLayoutListener(IrregularButton.this,this);
            }
        });

    }

    public void setTouchChecker(TouchChecker touchChecker)
    {
        this.touchChecker = touchChecker;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (touchChecker != null)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return touchChecker.isInTouchArea((int) event.getX(), (int) event.getY(), getWidth(), getHeight()) && super.onTouchEvent(event);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if(mBitmap!=null && !mBitmap.isRecycled()){
            mBitmap.recycle();
        }
    }
}
