package com.custom.yumiao.customviewpractice.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.custom.yumiao.customviewpractice.R;
import com.custom.yumiao.customviewpractice.view.viewutil.StartAnimator;

/**
 * Created by yumiaomiao on 2018/1/11.
 * 直播点赞动画view
 */

public class BezierView extends RelativeLayout implements StartAnimator{

    Drawable[] drawbles = new Drawable[5];
    private int measuredHeight;
    private int measuredWidth;
    private LayoutParams layoutparams;

    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        drawbles[0] = ContextCompat.getDrawable(getContext(), R.mipmap.ic1);
        drawbles[1] = ContextCompat.getDrawable(getContext(), R.mipmap.ic2);
        drawbles[2] = ContextCompat.getDrawable(getContext(), R.mipmap.ic3);
        drawbles[3] = ContextCompat.getDrawable(getContext(), R.mipmap.ic4);
        drawbles[4] = ContextCompat.getDrawable(getContext(), R.mipmap.ic5);
        //imageView 大小以及在布局中位置
        layoutparams = new LayoutParams(100, 100);
        layoutparams.addRule(CENTER_HORIZONTAL, TRUE);
        layoutparams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    public void addImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(drawbles[(int) (Math.random() * drawbles.length)]);//raandom 0.0-1.0之间
        iv.setLayoutParams(layoutparams);
        addView(iv);
        startScaleAnimator(iv);
        startBezier(iv);
    }


    /**
     * 开始缩放动画
     * @param view
     */
    public void startScaleAnimator(View view) {
        //imageview x or y Scale setting
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, SCALE_X, 0.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, 0.2f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());//设置为线性变化 匀速变化
        animatorSet.setDuration(500);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
    }

    /**
     * 开始贝塞尔曲线动画
     *
     * @param view
     */
    public void startBezier(final View view) {
        BezierEvaluator bezierEvaluator = new BezierEvaluator(getRandomPoint(), getRandomPoint());
        //view底部中心开始点
        PointF startP = new PointF(measuredWidth /2-50, measuredHeight);
        Log.d(getContext().getClass().getName(), "measuredWidth--" + measuredWidth+"---startP:"+startP.x);
        //view上部随机结束点
        PointF endP = new PointF((float) (Math.random() * measuredWidth), (float) (Math.random() * 50));
        ValueAnimator valueAnimator=ValueAnimator.ofObject(bezierEvaluator,startP,endP);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF=(PointF) animation.getAnimatedValue();
                view.setX(pointF.x);
                view.setY(pointF.y);
                view.setAlpha((float)1.0-animation.getAnimatedFraction());
            }


        });
        valueAnimator.start();


    }

    /**
     * 获得随机控制点
     *
     * @return
     */
    public PointF getRandomPoint() {
        PointF p = new PointF((float) Math.random() * measuredWidth, (float) Math.random() * measuredHeight / 4);
        return p;
    }

    @Override
    public void start() {
        addImageView();
    }

    public class BezierEvaluator implements TypeEvaluator<PointF> {

        private final PointF pointF1;
        private final PointF pointF2;

        public BezierEvaluator(PointF pointF1, PointF pointF2) {
            this.pointF1 = pointF1;
            this.pointF2 = pointF2;

        }

        @Override
        public PointF evaluate(float time, PointF startValue, PointF endValue) {
            float timeLeft = 1.0f - time;
            PointF point = new PointF();//结果
            //根据时间计算 点 在曲线的位置
            point.x = timeLeft * timeLeft * timeLeft * (startValue.x)
                    + 3 * timeLeft * timeLeft * time * (pointF1.x)
                    + 3 * timeLeft * time * time * (pointF2.x)
                    + time * time * time * (endValue.x);
            point.y = timeLeft * timeLeft * timeLeft * (startValue.y)
                    + 3 * timeLeft * timeLeft * time * (pointF1.y)
                    + 3 * timeLeft * time * time * (pointF2.y)
                    + time * time * time * (endValue.y);
            return point;

        }
    }


}
