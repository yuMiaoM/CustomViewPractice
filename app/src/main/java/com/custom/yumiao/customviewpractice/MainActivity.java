package com.custom.yumiao.customviewpractice;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.yumiao.customviewpractice.view.BezierView;
import com.custom.yumiao.customviewpractice.view.LauncherView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LauncherView launcherView= (LauncherView) findViewById(R.id.launcherview);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherView.startAnimation();
            }
        });
    }
}
