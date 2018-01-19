package com.custom.yumiao.customviewpractice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // startFragment(R.layout.fragment_waveview);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Fragment fragment = new BezierDemoFragment();
        ft.add(R.id.container, fragment);
        ft.commit();
    }

    public void startFragment(int id) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Fragment fragment = new BaseViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("layoutId", id);
        fragment.setArguments(bundle);
        ft.add(R.id.container, fragment);
        ft.commit();

    }

}
