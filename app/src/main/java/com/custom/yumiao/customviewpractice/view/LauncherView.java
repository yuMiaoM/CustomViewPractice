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
import com.custom.yumiao.customviewpractice.view.viewutil.ViewPath;
import com.custom.yumiao.customviewpractice.view.viewutil.ViewPoint;

import java.util.ArrayList;

/**
 * Created by yumiaomiao on 2018/1/16.
 * 启动页动画view
 */

public class LauncherView extends RelativeLayout {

    private int mWidth;
    private int mHeight;
    private ImageView red;
    private ImageView blue;
    private ImageView yellow;
    private ImageView green;
    private ViewPath redPath;
    private ViewPath bluePath;
    private ViewPath yellowPath;
    private ViewPath greenPath;


    public LauncherView(Context context) {
        this(context, null);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL, TRUE);
        lp.addRule(CENTER_VERTICAL, TRUE);

        red = new ImageView(getContext());
        red.setImageResource(R.drawable.red);
        red.setLayoutParams(lp);
        addView(red);

        blue = new ImageView(getContext());
        blue.setImageResource(R.drawable.blue);
        blue.setLayoutParams(lp);
        addView(blue);

        yellow = new ImageView(getContext());
        yellow.setImageResource(R.drawable.yellow);
        yellow.setLayoutParams(lp);
        addView(yellow);

        green = new ImageView(getContext());
        green.setImageResource(R.drawable.green);
        green.setLayoutParams(lp);
        addView(green);


    }

    public void setAnimation(ImageView view, ViewPath path) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(new ViewObject(view), "xy", new TypeEvaluator<ViewPoint>() {
            @Override
            public ViewPoint evaluate(float fraction, ViewPoint startValue, ViewPoint endValue) {
                float x = 0;
                float y = 0;
                x = (endValue.x - startValue.x) * fraction;
                y = (endValue.y - startValue.y) * fraction;
                Log.e("TAG", "x:" + x + "Y:" + y);
                return new ViewPoint(x, y);
            }
        }, path.getPoints().toArray());
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }

    public void startAnimation() {
        setAnimation(red, redPath);
        setAnimation(blue, bluePath);
        setAnimation(yellow, yellowPath);
        setAnimation(green, greenPath);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        initViewPath();

    }

    /**
     * 初始化viewpath lineTO
     */
    private void initViewPath() {
        redPath = new ViewPath();
        redPath.moveTo(0, 0);
        redPath.lineTo(mWidth / 5 - mWidth / 2, 0);

        bluePath = new ViewPath();
        bluePath.moveTo(0, 0);
        bluePath.lineTo(2 * mWidth / 5 - mWidth / 2, 0);

        yellowPath = new ViewPath();
        yellowPath.moveTo(0, 0);
        yellowPath.lineTo(mWidth / 2 - 2 * mWidth / 5, 0);

        greenPath = new ViewPath();
        greenPath.moveTo(0, 0);
        greenPath.lineTo(mWidth / 2 - mWidth / 5, 0);


    }

    class ViewObject {
        ImageView iv;

        public ViewObject(ImageView target) {
            this.iv = target;

        }

        public void setXy(ViewPoint vp) {
            iv.setTranslationX(vp.x);
            iv.setTranslationY(vp.y);


        }
    }

}
