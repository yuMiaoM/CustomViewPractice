package com.yumiao.layoutmanager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private List<String> mList;
    private ImageView imageView;
    private int lastX;
    private int lastY;
    private View view;
    private WindowManager wm;
    private WindowManager.LayoutParams wmParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        imageView = findViewById(R.id.iv);
        //  imageView.setOnTouchListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfloat("改变内容");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add("第" + i + "行");
        }
        MyRecylerViewAdapter adapter = new MyRecylerViewAdapter(this, mList);
        recyclerView.setAdapter(adapter);

    }

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private MotionEvent mCurrentDownEvent;
    private MotionEvent mPreviousUpEvent;

    public void showfloat(String s) {
        wm = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        view = LayoutInflater.from(this).inflate(R.layout.window_manager, null);
        /**
         * 获得屏幕尺寸
         */
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(s);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(view);
            }
        });


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() - 25;   //25是系统状态栏的高度
                Log.i("currP", "currX" + x + "====currY" + y);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                        if (mPreviousUpEvent != null
                                && mCurrentDownEvent != null
                                && isConsideredDoubleTap(mCurrentDownEvent,
                                mPreviousUpEvent, event)) {
                            wm.removeView(view);
                            return  true;
                        }
                        mCurrentDownEvent = MotionEvent.obtain(event);

                        //获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        Log.i("startP", "startX" + mTouchStartX + "====startY" + mTouchStartY);
                        break;

                    case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                        mPreviousUpEvent = MotionEvent.obtain(event);
                        /**
                         * 单击整个view
                         */
//                        if (mPreviousUpEvent.getEventTime()-mCurrentDownEvent.getEventTime()<200){
//                            wm.removeView(view);
//                            return true;
//                        }
                        updateViewPosition();
                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;

            }
        });

        /**
         *以下都是WindowManager.LayoutParams的相关属性
         * 具体用途可参考SDK文档
         */
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   //设置window type
        wmParams.format = PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明

        //设置Window flag
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 下面的flags属性的效果形同“锁定”。
         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | LayoutParams.FLAG_NOT_FOCUSABLE
                               | LayoutParams.FLAG_NOT_TOUCHABLE;
        */


        wmParams.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角，便于调整坐标
        //以屏幕左上角为原点，设置x、y初始值
        wmParams.x = width / 2 - 150;
        wmParams.y = height / 2 - 100;

        //设置悬浮窗口长宽数据
        wmParams.width = 300;
        wmParams.height = 200;

        //显示myFloatView图像
        wm.addView(view, wmParams);


    }

    /* 判断是否有长按动作发生
     * @param lastX 按下时X坐标
     * @param lastY 按下时Y坐标
     * @param thisX 移动时X坐标
     * @param thisY 移动时Y坐标
     * @param lastDownTime 按下时间
     * @param thisEventTime 移动时间
     * @param longPressTime 判断长按时间的阀值
     */

    private boolean isLongPressed(float lastX, float lastY,
                                  float thisX, float thisY,
                                  long lastDownTime, long thisEventTime,
                                  long longPressTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            return true;
        }
        return false;
    }

    /**
     * 双击
     *
     * @param firstDown
     * @param firstUp
     * @param secondDown
     * @return
     */
    private boolean isConsideredDoubleTap(MotionEvent firstDown,
                                          MotionEvent firstUp, MotionEvent secondDown) {
        if (secondDown.getEventTime() - firstUp.getEventTime() > 200) {
            return false;
        }
        int deltaX = (int) firstUp.getX() - (int) secondDown.getX();
        int deltaY = (int) firstUp.getY() - (int) secondDown.getY();
        return deltaX * deltaX + deltaY * deltaY < 10000;
    }

    private void updateViewPosition() {
        //更新浮动窗口位置参数
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(view, wmParams);  //刷新显示
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.e("TAG", "getX:" + x + "--getY:" + y + "++++getRawX:" + event.getRawX() + "--getRawY:" + event.getRawY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                // 在当前left、top、right、bottom的基础上加上偏移量
                imageView.layout(imageView.getLeft() + offsetX,
                        imageView.getTop() + offsetY,
                        imageView.getRight() + offsetX,
                        imageView.getBottom() + offsetY);
                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        return true;

    }
}