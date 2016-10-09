package com.hc.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.hc.library.R;
import com.hc.library.util.DensityUtils;

/**
 * Created by xc on 2016/8/26.
 */
public class RoundCornerImageView extends ImageView {

    private final static int ROUND_LEFT_TOP = 0;
    private final static int ROUND_RIGHT_TOP = 1;
    private final static int ROUND_RIGHT_BOTTOM = 2;
    private final static int ROUND_LEFT_BOTTOM = 3;


    private RectF mRectF;
    private Path mClipPath;
    private float[] mRounds;
    private boolean mIsOval = true;

    public RoundCornerImageView(Context context) {
        this(context,null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs,
                R.attr.RoundCornerImageViewStyle);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);

        mRectF = new RectF(0,0,0,0);
        mClipPath = new Path();

        if(isInEditMode()){
            return;
        }

        mRounds = new float[4];

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.RoundCornerImageView,defStyleAttr,0);

        float density = DensityUtils.getDensity(getContext());

        float round = a.getDimension(R.styleable.RoundCornerImageView_round,0) * density;
        int len = mRounds.length;
        for(int i=0;i<len;i++){
            mRounds[i] = round;
        }

        mRounds[ROUND_LEFT_TOP] = a.getDimension(R.styleable.RoundCornerImageView_roundLeftTop,mRounds[ROUND_LEFT_TOP]) * density;
        mRounds[ROUND_RIGHT_TOP] = a.getDimension(R.styleable.RoundCornerImageView_roundRightTop,mRounds[ROUND_RIGHT_TOP]) * density;
        mRounds[ROUND_RIGHT_BOTTOM] = a.getDimension(R.styleable.RoundCornerImageView_roundRightBottom,mRounds[ROUND_RIGHT_BOTTOM]) * density;
        mRounds[ROUND_LEFT_BOTTOM] = a.getDimension(R.styleable.RoundCornerImageView_roundLeftBottom,mRounds[ROUND_LEFT_BOTTOM]) * density;

        mIsOval = a.getBoolean(R.styleable.RoundCornerImageView_isOva,mIsOval);

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth();
        int h = this.getHeight();
        mClipPath.reset();
        mRectF.right = w;
        mRectF.bottom = h;
        if(mIsOval){
            mClipPath.addOval(mRectF, Path.Direction.CW);
        }else {
            mClipPath.addRoundRect(mRectF,mRounds,Path.Direction.CW);
        }
        canvas.clipPath(mClipPath);

        super.onDraw(canvas);
    }

    public void setIsOval(boolean isOval){
        mIsOval = isOval;
        invalidate();
    }
}
