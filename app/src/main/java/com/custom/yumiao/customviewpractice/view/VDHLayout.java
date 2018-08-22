package com.custom.yumiao.customviewpractice.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class VDHLayout extends LinearLayout {


    private ViewDragHelper vdh;

    public VDHLayout(Context context) {
        this(context, null);
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VDHLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        vdh = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.e("TAG","pointerId="+pointerId+"childId="+child.getId());

                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.e("TAG","left="+left);

                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.e("TAG","top="+top);


                return top;
            }
        });

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return vdh.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        vdh.processTouchEvent(event);
        return true;
    }


}
