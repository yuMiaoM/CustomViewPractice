package com.custom.yumiao.customviewpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.yumiao.customviewpractice.model.MainData;
import com.custom.yumiao.customviewpractice.view.viewutil.AppBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private List<MainData> mainDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        lv = (ListView) findViewById(R.id.lv);
        MainAdapter mainAdapter=new MainAdapter(mainDatas,this);
        lv.setAdapter(mainAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainData mainData = mainDatas.get(position);
                Intent intent=new Intent(MainActivity.this,ContainerActivity.class);
                intent.putExtra("layoutId",mainData.getLayoutId());
                startActivity(intent);
            }
        });



    }

    private void initData() {
        mainDatas=new ArrayList<>();
        mainDatas.add(new MainData("添加购物车动画",R.layout.fragment_addshopbutton));
        mainDatas.add(new MainData("点赞动画",R.layout.fragment_bezierview));
        mainDatas.add(new MainData("启动动画",R.layout.fragment_launcherview));
        mainDatas.add(new MainData("水波动画",R.layout.fragment_waveview));
        mainDatas.add(new MainData("测试动画",R.layout.fragment_vdhview));


    }

    public class MainAdapter extends AppBaseAdapter<MainData> {

        public MainAdapter(List<MainData> list, Context context) {
            super(list, context);
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_lv_main, null);
                vh.tv = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(vh);

            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            MainData data = list.get(position);
            vh.tv.setText(data.getTitle());
            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }

    }

}
