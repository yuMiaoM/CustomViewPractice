package com.custom.yumiao.customviewpractice.view;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.custom.yumiao.customviewpractice.R;

import java.util.ArrayList;

/**
 * Created by yumiaomiao on 2018/1/16.
 * 启动页动画view
 */

public class LauncherView extends RelativeLayout {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private ImageView red;


    public LauncherView(Context context) {
        this(context, null);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL,TRUE);
        lp.addRule(CENTER_VERTICAL,TRUE);

        red =new ImageView(getContext());
        red.setImageResource(R.drawable.red);
        red.setLayoutParams(lp);
        addView(red);

        ArrayList<PointF> pointFs=new ArrayList<>();
        pointFs.add(new PointF(0.0f,0.0f));
        pointFs.add(new PointF(100f,100f));
        pointFs.add(new PointF(200f,200f));
        pointFs.add(new PointF(300,300f));
        pointFs.add(new PointF(400f,100f));

        ObjectAnimator objectAnimator=ObjectAnimator.ofObject(red,"", new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                Log.d("TAG","fraction:"+fraction+"-startValue:"+startValue.x+"-endValue:"+endValue.x);
                red.setX(endValue.x);
                red.setY(endValue.y);
                return null;
            }
        }
        , pointFs.toArray());
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth =getMeasuredWidth();
        mHeight =getMeasuredHeight();

    }
}
