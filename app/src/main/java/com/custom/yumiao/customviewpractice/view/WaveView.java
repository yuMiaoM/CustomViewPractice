package com.custom.yumiao.customviewpractice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.custom.yumiao.customviewpractice.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yumiaomiao on 2018/1/12.
 * 水流波浪效果
 */

public class WaveView extends View {


    private int color;
    private Paint mPaint;
    private int len;
    private float[] array2;
    private float[] array1;
    private int move;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        color = typedArray.getColor(R.styleable.WaveView_wavecolor, Color.BLUE);
        typedArray.recycle();
        init();
        moveWaterLine();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        len = Math.min(width, height);
        setMeasuredDimension(len, len);
        //第一条以及第二条波浪线y轴的值
        array1 = new float[len];
        array2 = new float[len];

    }

    public void moveWaterLine() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                //不断改变初相
                move += 1;
                //重新绘制(子线程中调用)
                postInvalidate();
            }
        }, 500, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
        // 将周期定为view总宽度
        float mCycleFactorW = (float) (2 * Math.PI / len);
        for(int i =0 ;i<len ;i++){
            array1[i]=(float)( 10*Math.sin(i*mCycleFactorW+move));
        }
        for(int i =0 ;i<len ;i++){
            array2[i]=(float)( 15*Math.sin(i*mCycleFactorW+10+move));
        }
        // 裁剪成圆形区域
        Path path = new Path();
        path.reset();
        float clipRadius=len/2;
        //添加圆形路径
        //Path.Direction.CCW逆时针
        //Path.Direction.CW顺时针
        path.addCircle(len / 2, len / 2, clipRadius, Path.Direction.CCW);
        // (剪裁路径)裁剪成圆形区域
        //(REPLACE用当前要剪切的区域代替画布中的内容的区域)
        canvas.clipPath(path, android.graphics.Region.Op.REPLACE);

        canvas.save();
        canvas.translate(0,len/2);

        for(int i = 0 ;i<len;i++){
            canvas.drawLine(i,array1[i],i,len,mPaint);
        }
        for(int i = 0 ;i<len;i++){
            canvas.drawLine(i,array2[i],i,len,mPaint);
        }
        canvas.restore();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
    }


}
