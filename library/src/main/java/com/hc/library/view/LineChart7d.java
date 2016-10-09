package com.hc.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Wqz on 2016/9/1.
 */

public class LineChart7d extends View
{
    final int LINE_WIDTH = 3;
    int[] WeeksTem = null;
    int[] WeeksTemLow = null;
    boolean isSeted = false;

    Context cWeatherActivity;

    public LineChart7d(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        cWeatherActivity = context;
    }

    public LineChart7d(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        cWeatherActivity = context;
    }

    public LineChart7d(Context context)
    {
        super(context);
        cWeatherActivity = context;
    }

    public void setAll(int[] m_WeeksTem, int[] m_WeeksTemLow)
    {
        this.WeeksTem = m_WeeksTem;
        this.WeeksTemLow = m_WeeksTemLow;

        isSeted = true;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(!isSeted) return;

        //画布透明
        canvas.drawColor(Color.argb(0x00, 0x00, 0x00, 0x00));

        Paint lineTopBrush = new Paint();
        lineTopBrush.setColor(Color.parseColor("#55A1EF"));
        lineTopBrush.setAntiAlias(true);
        lineTopBrush.setStrokeWidth(LINE_WIDTH);

        Paint lineBottomBrush = new Paint();
        lineBottomBrush.setColor(Color.parseColor("#87C0FF"));
        lineBottomBrush.setAntiAlias(true);
        lineBottomBrush.setStrokeWidth(LINE_WIDTH);


        int perWidth = canvas.getWidth() / 6;
        int x = 0;
        int minTem = 100, maxTem = -100;
        int minLowTem = 100,maxLowTem = -100;

        for(int i = 0;i < 6;i++)
        {
            if(minTem > WeeksTem[i]) minTem = WeeksTem[i];
            if(maxTem < WeeksTem[i]) maxTem = WeeksTem[i];

            if(minLowTem > WeeksTemLow[i]) minLowTem = WeeksTemLow[i];
            if(maxLowTem < WeeksTemLow[i]) maxLowTem = WeeksTemLow[i];
        }

        for(int i = 0;i < 5;i++)
        {
            x = perWidth / 2 + i * perWidth;

            if(maxTem - minTem != 0)
            {
                canvas.drawLine(
                        x,
                        (int)((maxTem - WeeksTem[i]) * 1.0 /
                                (maxTem - minTem) * perWidth / 2.0 + 0.1 * perWidth),
                        x + perWidth,
                        (int)((maxTem - WeeksTem[i + 1]) * 1.0 /
                                (maxTem - minTem) * perWidth / 2.0 + 0.1 * perWidth),
                        lineTopBrush);

                if(i == 0)
                {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(LINE_WIDTH);
                    paint.setColor(Color.WHITE);
                    Path path = new Path();
                    path.moveTo(x, (int)((maxTem - WeeksTem[i]) * 1.0 / (maxTem - minTem) * perWidth / 2.0 + 0.1 * perWidth));
                    path.lineTo(x + perWidth, (int)((maxTem - WeeksTem[i + 1]) * 1.0 / (maxTem - minTem) * perWidth / 2.0 + 0.1 * perWidth));
                    PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
                    paint.setPathEffect(effects);
                    canvas.drawPath(path, paint);
                }
            }
            else
            {
                canvas.drawLine(
                        x, (int) (perWidth / 2.0 + 0.1 * perWidth),
                        x + perWidth, (int) (perWidth / 2.0 + 0.1 * perWidth),
                        lineTopBrush);

                if(i == 0)
                {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(LINE_WIDTH);
                    paint.setColor(Color.WHITE);
                    Path path = new Path();
                    path.moveTo(x, (int) (perWidth / 2.0 + 0.1 * perWidth));
                    path.lineTo(x + perWidth, (int) (perWidth / 2.0 + 0.1 * perWidth));
                    PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
                    paint.setPathEffect(effects);
                    canvas.drawPath(path, paint);
                }
            }
            if(maxLowTem - minLowTem != 0)
            {
                canvas.drawLine(
                        x,
                        (int)
                                ((maxLowTem - WeeksTemLow[i]) * 1.0 / (maxLowTem - minLowTem)
                                        * perWidth / 2.0 + perWidth),
                        x + perWidth,
                        (int)
                                ((maxLowTem - WeeksTemLow[i + 1]) * 1.0 / (maxLowTem - minLowTem)
                                        * perWidth / 2.0 + perWidth),
                        lineBottomBrush);

                if(i == 0)
                {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(LINE_WIDTH);
                    paint.setColor(Color.WHITE);
                    Path path = new Path();
                    path.moveTo(x, (int) ((maxLowTem - WeeksTemLow[i]) * 1.0 / (maxLowTem - minLowTem) * perWidth / 2.0 + perWidth));
                    path.lineTo(x + perWidth, (int) ((maxLowTem - WeeksTemLow[i + 1]) * 1.0 / (maxLowTem - minLowTem) * perWidth / 2.0 + perWidth));
                    PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
                    paint.setPathEffect(effects);
                    canvas.drawPath(path, paint);
                }
            }
            else
            {
                canvas.drawLine(
                        x,
                        (int) (perWidth / 2.0 + perWidth),
                        x + perWidth,
                        (int) (perWidth / 2.0 + perWidth),
                        lineBottomBrush);

                if(i == 0)
                {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(LINE_WIDTH);
                    paint.setColor(Color.WHITE);
                    Path path = new Path();
                    path.moveTo(x, (int) (perWidth / 2.0 + perWidth));
                    path.lineTo(x + perWidth, (int) (perWidth / 2.0 + perWidth));
                    PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
                    paint.setPathEffect(effects);
                    canvas.drawPath(path, paint);
                }
            }
        }

        lineTopBrush.setStrokeWidth(10);
        lineBottomBrush.setStrokeWidth(10);

        Paint pointBrush = new Paint();
        pointBrush.setColor(Color.WHITE);
        pointBrush.setAntiAlias(true);
        pointBrush.setStrokeWidth(6);

        for(int i = 0;i < 6;i++)
        {
            x = perWidth / 2 + i * perWidth;

            canvas.drawCircle(x, (int)((maxTem - WeeksTem[i]) * 1.0 /
                            (maxTem - minTem) * perWidth / 2.0 + 0.1 * perWidth), 10, lineTopBrush);
            canvas.drawCircle(x, (int) ((maxLowTem - WeeksTemLow[i]) * 1.0 / (maxLowTem - minLowTem)
                                    * perWidth / 2.0 + perWidth), 10,lineBottomBrush);

            canvas.drawCircle(x, (int)((maxTem - WeeksTem[i]) * 1.0 /
                    (maxTem - minTem) * perWidth / 2.0 + 0.1 * perWidth), 6, pointBrush);
            canvas.drawCircle(x, (int) ((maxLowTem - WeeksTemLow[i]) * 1.0 / (maxLowTem - minLowTem)
                                    * perWidth / 2.0 + perWidth), 6, pointBrush);

        }
    }
}
