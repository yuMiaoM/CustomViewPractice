package com.custom.yumiao.customviewpractice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContainerActivity extends AppCompatActivity {


    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        startFragment(getIntent().getIntExtra("layoutId",0));
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
