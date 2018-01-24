package com.custom.yumiao.customviewpractice.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.custom.yumiao.customviewpractice.R;
import com.custom.yumiao.customviewpractice.view.viewutil.StartAnimator;

/**
 * Created by yumiaomiao on 2018/1/22.
 */

public class AddShopButton extends View implements StartAnimator {


    private final float mTextSize;
    private int mTextColor;
    private int mTextBg;
    private Paint mTextPaint;
    private Paint mTextBgPaint;
    private float bgAniValue;
    private int mWidth;
    private int mHeight;
    private RectF bgRectF;
    private String mText = "无标题";
    private float aFloat;
    private float mTextWith;
    private float mAddPadding;
    private float mCorners;
    private boolean isHide = false;
    private boolean isBganistart = false;
    private float roundAniTime;
    private ValueAnimator roundAni;
    private Region addRegion;
    private Region reduceRegion;
    private ValueAnimator valueAnimator;
    private Rect addF;
    private Rect reduceF;
    private boolean isFinish;
    private int count = 0;

    public AddShopButton(Context context) {
        this(context, null);
    }

    public AddShopButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddShopButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddShopButton);
        mTextColor = typedArray.getColor(R.styleable.AddShopButton_add_text_color, context.getResources().getColor(R.color.black));
        mTextBg = typedArray.getColor(R.styleable.AddShopButton_add_text_bg, context.getResources().getColor(R.color.lightskyblue));
        mText = typedArray.getString(R.styleable.AddShopButton_add_text);
        mTextSize = typedArray.getDimension(R.styleable.AddShopButton_add_text_size, 20f);
        mAddPadding = typedArray.getDimension(R.styleable.AddShopButton_add_padding, 5f);
        mCorners = typedArray.getDimension(R.styleable.AddShopButton_add_corners, 10f);

        typedArray.recycle();
        init();

    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(6);
        mTextPaint.setTextSize(mTextSize);

        mTextBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextBgPaint.setColor(mTextBg);
        mTextBgPaint.setStyle(Paint.Style.FILL);
        mTextBgPaint.setStrokeWidth(4);

        addRegion = new Region();
        reduceRegion = new Region();

    }

    public void drawRound(final boolean isLeft) {
        if (isLeft) {
            roundAni = ValueAnimator.ofFloat(0, 1);
        } else {
            roundAni = ValueAnimator.ofFloat(1, 0);
        }
        roundAni.setDuration(500);
        roundAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                roundAniTime = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        roundAni.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isFinish = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isLeft) {
                    isFinish = true;
                } else {
                    drawRectBg(false);
                }
            }
        });
        roundAni.start();
    }


    public void drawRectBg(final boolean isAdd) {
        if (isAdd) {
            valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        } else {
            valueAnimator = ValueAnimator.ofFloat(1f, 0f);
        }
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bgAniValue = (float) animation.getAnimatedValue();
                invalidate();
            }


        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isBganistart = true;
                invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isHide = true;
                if (isAdd) {
                    drawRound(true);
                } else {
                    isBganistart = false;
                    isHide = false;
                }
                invalidate();
            }
        });
        valueAnimator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "onTouchEvent: " + event.getX() + "---" + event.getY());
                if (!isBganistart) {
                    onAddClick();
                    return true;
                } else {
                    if (addF.contains((int) event.getX(), (int) event.getY())) {
                        onAddClick();
                        return true;
                    } else if (reduceF.contains((int) event.getX(), (int) event.getY())) {
                        onDelClick();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;


        }


        return super.onTouchEvent(event);
    }

    private void onDelClick() {
        Toast.makeText(getContext(), "点击删除", Toast.LENGTH_SHORT).show();
        if (count == 0) {
            drawRound(false);
        } else {
            count--;
            invalidate();
        }


    }

    private void onAddClick() {
        if (!isBganistart) {
            drawRectBg(true);
        }
        if (isHide) {
            Toast.makeText(getContext(), "点击增加", Toast.LENGTH_SHORT).show();
            count++;
            invalidate();
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float l = mWidth * bgAniValue + mAddPadding;
        float t = mAddPadding;
        float r = mWidth - mAddPadding;
        float b = mHeight - mAddPadding;
        float width = r - l;// bgrectF的宽
        float height = b - t;// bgrectF的高

        bgRectF = new RectF(l, t, r, b);
        mTextBgPaint.setStyle(Paint.Style.FILL);
        mTextBgPaint.setAlpha(255);
        canvas.drawRoundRect(bgRectF, mCorners, mCorners, mTextBgPaint);
        Log.e("TAG", l + "-" + t + "-" + r + "-" + b+ "-" + "-" + "-");

        mTextWith = mTextPaint.measureText(mText);
        if (!isBganistart) {
            //计算text 位置  mTextPaint.descent()+mTextPaint.ascent() 基线的上移与下移的和 为文字的高度
            canvas.drawText(mText, mWidth / 2 - mTextWith / 2, mHeight / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2, mTextPaint);
        }
        if (isFinish) {
            String strCount = String.valueOf(count);
            float textWidth = mTextPaint.measureText(strCount);
            float textHeight = mTextPaint.ascent() + mTextPaint.descent();

            float x = mWidth / 2 - textWidth / 2;
            float y = mHeight / 2 - textHeight / 2;
            canvas.drawText(strCount, x, y, mTextPaint);
        }
        if (isHide) {
            float x = mWidth - height / 2 - mAddPadding;
            float y = mAddPadding + height / 2;
            canvas.drawCircle(x, y, height / 2, mTextBgPaint);
            canvas.drawLine(x - height / 4, y, x + height / 4, y, mTextPaint);
            canvas.drawLine(x, y - height / 4, x, y + height / 4, mTextPaint);
            addF = new Rect((int) (x - height / 2), (int) (y - height / 2), (int) (x + height / 2), (int) (y + height / 2));
            canvas.save();

            float translats = x - y;
            canvas.translate(x - translats * roundAniTime, y);
            canvas.rotate(-roundAniTime * 360);
            mTextBgPaint.setStyle(Paint.Style.STROKE);
            mTextBgPaint.setAlpha((int) (255 * roundAniTime));
            canvas.drawCircle(0, 0, height / 2, mTextBgPaint);
            canvas.drawLine(-height / 4, 0, height / 4, 0, mTextPaint);
            canvas.save();

            //点击区域起点 为屏幕左上角
            reduceF = new Rect(0, 0, (int) height, (int) height);
        }


    }

    @Override
    public void start() {

    }
}
