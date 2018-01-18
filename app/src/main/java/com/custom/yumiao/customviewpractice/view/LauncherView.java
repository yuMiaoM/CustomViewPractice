package com.custom.yumiao.customviewpractice.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.custom.yumiao.customviewpractice.R;
import com.custom.yumiao.customviewpractice.view.viewutil.StartAnimator;
import com.custom.yumiao.customviewpractice.view.viewutil.ViewPath;
import com.custom.yumiao.customviewpractice.view.viewutil.ViewPathEvaluator;
import com.custom.yumiao.customviewpractice.view.viewutil.ViewPoint;

import java.util.ArrayList;

/**
 * Created by yumiaomiao on 2018/1/16.
 * 启动页动画view
 */

public class LauncherView extends RelativeLayout implements StartAnimator {

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
    private ImageView logo;
    private LayoutParams lp;
    private AnimatorSet animatorSet1;
    private AnimatorSet animatorSet2;
    private AnimatorSet animatorSet3;
    private AnimatorSet animatorSet4;


    public LauncherView(Context context) {
        this(context, null);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // init();
    }

    private void init() {
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL, TRUE);
        lp.addRule(CENTER_VERTICAL, TRUE);




        red = new ImageView(getContext());
        red.setImageResource(R.drawable.red);
        red.setLayoutParams(lp);
        red.setTag("red");
        addView(red);

        blue = new ImageView(getContext());
        blue.setImageResource(R.drawable.blue);
        blue.setLayoutParams(lp);
        blue.setTag("blue");
        addView(blue);

        yellow = new ImageView(getContext());
        yellow.setImageResource(R.drawable.yellow);
        yellow.setLayoutParams(lp);
        yellow.setTag("yellow");
        addView(yellow);

        green = new ImageView(getContext());
        green.setImageResource(R.drawable.green);
        green.setLayoutParams(lp);
        green.setTag("green");
        addView(green);


    }

    public AnimatorSet setAnimation(ImageView view, ViewPath path) {
        ViewPathEvaluator customEvaluator=new ViewPathEvaluator();
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(new ViewObject(view), "xy",customEvaluator, path.getPoints().toArray());
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(2600);
        return addAnimator(objectAnimator,view);
    }

    public  class  CustomEvaluator implements TypeEvaluator<ViewPoint>{

        @Override
        public ViewPoint evaluate(float t, ViewPoint startValue, ViewPoint endValue) {
            float x = 0;
            float y = 0;

            if (endValue.operation == ViewPath.LINE) {
                x = (endValue.x - startValue.x) * t;
                y = (endValue.y - startValue.y) * t;
            } else if (endValue.operation == ViewPath.CURVE) {
                float oneMinusT = 1 - t;
                x = oneMinusT * oneMinusT * oneMinusT * startValue.x +
                        3 * oneMinusT * oneMinusT * t * endValue.x +
                        3 * oneMinusT * t * t * endValue.x1 +
                        t * t * t * endValue.x2;

                y = oneMinusT * oneMinusT * oneMinusT * startValue.x +
                        3 * oneMinusT * oneMinusT * t * endValue.y +
                        3 * oneMinusT * t * t * endValue.y1 +
                        t * t * t * endValue.y2;
            }

            Log.e("TAG","view:"+""+"t:"+t+ "x:" + x + "Y:" + y+"endValue.operation:"+endValue.operation);
            return new ViewPoint(x, y);
        }
    }


    private AnimatorSet addAnimator(ObjectAnimator objectAnimator, final ImageView view) {
        AnimatorSet set=new AnimatorSet();
        ValueAnimator va=ValueAnimator.ofFloat(1,1000);
        va.setDuration(1800);
        va.setStartDelay(1000);
        va.addListener(new AnimEndListener(view));
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float alpha = 1 - value / 2000;
                float scale = getScale(view) - 1;
                if (value <= 500) {
                    scale = 1 + (value / 500) * scale;
                } else {
                    scale = 1 + ((1000 - value) / 500) * scale;
                }
                view.setScaleX(scale);
                view.setScaleY(scale);
                view.setAlpha(alpha);
            }
        });
        set.playTogether(objectAnimator,va);
        return set;

    }

    private float getScale(ImageView target) {
        if (target == red)
            return 3.0f;
        if (target == blue)
            return 2.0f;
        if (target == yellow)
            return 4.5f;
        if (target == blue)
            return 3.5f;
        return 2f;
    }

    public void startAnimation() {
        removeAllViews();
        init();
        animatorSet1 = setAnimation(red, redPath);
        animatorSet2 = setAnimation(blue, bluePath);
        animatorSet3 = setAnimation(yellow, yellowPath);
        animatorSet4 = setAnimation(green, greenPath);

        animatorSet1.start();
        animatorSet2.start();
        animatorSet3.start();
        animatorSet4.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogo();
            }
        }, 2400);

    }

    private void showLogo() {

        logo=new ImageView(getContext());
        logo.setImageResource(R.mipmap.chrome);
        logo.setLayoutParams(lp);
        addView(logo);
        ObjectAnimator oa=ObjectAnimator.ofFloat(logo,View.ALPHA,0f,1.0f);
        oa.setDuration(800);
        oa.start();
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
        redPath.curveTo(-700, -mHeight / 2, mWidth / 3 * 2, -mHeight / 3 * 2, 0, 0);

        bluePath = new ViewPath();
        bluePath.moveTo(0, 0);
        bluePath.lineTo(2 * mWidth / 5 - mWidth / 2, 0);
        bluePath.curveTo(-300, -mHeight / 2, mWidth, -mHeight / 9 * 5, 0, 0);

        yellowPath = new ViewPath();
        yellowPath.moveTo(0, 0);
        yellowPath.lineTo(mWidth / 2 - 2 * mWidth / 5, 0);
        yellowPath.curveTo(300, mHeight, -mWidth, -mHeight / 9 * 5, 0, 0);

        greenPath = new ViewPath();
        greenPath.moveTo(0, 0);
        greenPath.lineTo(mWidth / 2 - mWidth / 5, 0);
        greenPath.curveTo(700, mHeight / 3 * 2, -mWidth / 2, mHeight / 2, 0, 0);


    }

    @Override
    public void start() {
        startAnimation();
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView((target));
        }
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
