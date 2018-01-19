package com.custom.yumiao.customviewpractice;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.custom.yumiao.customviewpractice.view.viewutil.StartAnimator;

import java.util.ArrayList;

/**
 * Created by yumiaomiao on 2018/1/18.
 */

public class BaseViewFragment extends Fragment {

    private Bundle arguments;
    private int layoutId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arguments = getArguments();
        layoutId = arguments.getInt("layoutId");
        View view = inflater.inflate(layoutId, container, false);
        final StartAnimator startAnimator = (StartAnimator) view.findViewById(R.id.view);
        final Button start = (Button) view.findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimator.start();
            }
        });

        return view;
    }

}
