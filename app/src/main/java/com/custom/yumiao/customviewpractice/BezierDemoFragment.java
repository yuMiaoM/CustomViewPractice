package com.custom.yumiao.customviewpractice;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.custom.yumiao.customviewpractice.view.BezierSampleDemo;
import com.custom.yumiao.customviewpractice.view.viewutil.StartAnimator;

/**
 * Created by yumiaomiao on 2018/1/18.
 */

public class BezierDemoFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bezierdemo, container, false);
        final BezierSampleDemo bsd= (BezierSampleDemo) view.findViewById(R.id.view);
        RadioGroup rg= (RadioGroup) view.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rb_1){
                    bsd.setMode(true);
                }else {
                    bsd.setMode(false);
                }

            }
        });
        return view;
    }

}
