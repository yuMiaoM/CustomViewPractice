package com.custom.yumiao.customviewpractice.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.custom.yumiao.customviewpractice.R;

/**
 * Created by yumiaomiao on 2018/1/19.
 * 贝塞尔演示界面
 */

public class BezierSampleDemo extends View {

    public boolean mode = true;
    private int centerX;
    private int centerY;
    private PointF start, end, control1, control2;
    private Paint mPaint;
    private Path mPath;

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public BezierSampleDemo(Context context) {
        this(context, null);
    }

    public BezierSampleDemo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        start = new PointF();
        end = new PointF();
        control1 = new PointF();
        control2 = new PointF();
        mPath = new Path();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mode) {
            control1.x = event.getX();
            control1.y = event.getY();
        } else {
            control2.x = event.getX();
            control2.y = event.getY();
        }
        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        start.x = centerX - w / 4;
        start.y = centerY;
        end.x = centerX + w / 4;
        end.y = centerY;

        control1.x = centerX - 100;
        control1.y = centerY - 100;

        control2.x = centerX + 100;
        control2.y = centerY - 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPaint.setColor(getResources().getColor(R.color.google_blue));
        mPaint.setStrokeWidth(5);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        canvas.drawLine(control1.x, control1.y, control2.x, control2.y, mPaint);
        canvas.drawLine(control2.x, control2.y, end.x, end.y, mPaint);
        mPaint.setColor(getResources().getColor(R.color.google_red));
        mPaint.setStrokeWidth(10);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control1.x, control1.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);
        mPaint.setColor(getResources().getColor(R.color.google_green));
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.moveTo(start.x, start.y);
        mPath.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);
        canvas.drawPath(mPath, mPaint);


    }
}
