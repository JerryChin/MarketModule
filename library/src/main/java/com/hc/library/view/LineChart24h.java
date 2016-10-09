package com.hc.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wqz on 2016/9/2.
 */

public class LineChart24h extends View
{
    Context cWeatherActivity;
    int[] WeeksTem;
    String[] Label;
    boolean isSeted = false;
    int nTextSize;
    int HVScreenWidth;
    int HVScreenHeight;

    public LineChart24h(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        cWeatherActivity = context;
    }

    public LineChart24h(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        cWeatherActivity = context;
    }

    public LineChart24h(Context context)
    {
        super(context);
        cWeatherActivity = context;
    }

    public void setAll(int[] m_WeeksTem,String[] m_Label)
    {
        this.WeeksTem = m_WeeksTem;
        this.Label = m_Label;
        isSeted = true;

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        setMeasuredDimension(HVScreenWidth, HVScreenHeight);
    }

    public void setWidthHeight(int width, int height) {
        this.HVScreenHeight = height;
        this.HVScreenWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(!isSeted) return;
        nTextSize = canvas.getWidth() / 100;

        //画布透明
        canvas.drawColor(Color.argb(0x00, 0x00, 0x00, 0x00));

        Paint lineBrush = new Paint();
        lineBrush.setColor(Color.parseColor("#000000"));
        lineBrush.setAntiAlias(true);
        lineBrush.setStrokeWidth(2);

        Paint pointBrush = new Paint();
        pointBrush.setColor(Color.parseColor("#5551A6FF"));
        pointBrush.setAntiAlias(true);
        pointBrush.setStrokeWidth(1);

        Paint textBrush = new Paint();
        //textBrush.setColor(Color.parseColor("#A5A5A5"));
        textBrush.setColor(Color.parseColor("#000000"));
        textBrush.setAntiAlias(true);
        textBrush.setTextSize(nTextSize);

        int perWidth = canvas.getWidth() / 24;
        int halfPerWidth = canvas.getWidth() / 48;
        List<Integer> x = new ArrayList<Integer>();
        List<Integer> y = new ArrayList<Integer>();

        //---------得到最大最小温度----------
        int minTem = 100,maxTem = -100;
        for(int i = 0;i < 24;i++)
        {
            if(WeeksTem[i] < minTem)minTem = WeeksTem[i];
            if(WeeksTem[i] > maxTem)maxTem = WeeksTem[i];
        }

        for(int i = 0;i < 24;i++)
        {
            x.add(i * perWidth + halfPerWidth);
            y.add((int)(halfPerWidth + ((maxTem - WeeksTem[i]) * 1.0 / (maxTem - minTem)) * perWidth));
        }

        //-------- 折线 ------------
        canvas.drawLine(0,perWidth,x.get(0),y.get(0),lineBrush);
        for(int i = 0;i < 23;i++)
            canvas.drawLine(x.get(i),y.get(i),x.get(i+1),y.get(i+1),lineBrush);
        canvas.drawLine(x.get(23),y.get(23),canvas.getWidth(),perWidth,lineBrush);

        //---------竖线-------------
        lineBrush.setStrokeWidth(1);
        for (int i = 0;i < 24;i++)
        {
            canvas.drawLine(x.get(i),y.get(i),x.get(i),halfPerWidth * 5,lineBrush);

            textBrush.setColor(Color.parseColor("#000000"));
            canvas.drawText(WeeksTem[i] + "°",(int)(x.get(i) - (intLength(WeeksTem[i]) * 0.3) * nTextSize)
                    ,y.get(i) - (int)(nTextSize * 0.5),textBrush);

            pointBrush.setColor(Color.parseColor("#5551A6FF"));
            canvas.drawCircle(x.get(i),y.get(i),20,pointBrush);

            pointBrush.setColor(Color.parseColor("#51A6FF"));
            canvas.drawCircle(x.get(i),y.get(i),15,pointBrush);

            textBrush.setColor(Color.parseColor("#A5A5A5"));
            canvas.drawText(Label[i],x.get(i) - (int)(Label[i].length() * 0.5 * nTextSize),halfPerWidth * 5 + nTextSize,textBrush);
        }

        //----------------下方横线--------------------
        canvas.drawLine(0,halfPerWidth * 5,canvas.getWidth(),halfPerWidth * 5,lineBrush);
    }

    int intLength(int n)
    {
        if(n >= 0 && n < 10)
            return 1;
        else if(n >= 10 && n < 100)
            return 2;
        else if(n > -10 && n < 0)
            return 2;
        else
            return 3;
    }
}
