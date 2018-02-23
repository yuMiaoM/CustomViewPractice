package com.custom.yumiao.customviewpractice.view.viewutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @说 明：
 * @项目名称：1506adv
 * @类 名： AppBaseAdapter
 * @版 本：v1.0
 * @修 改人：
 * @修改时间：
 * @修改备注：
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {
    public List<T> list;
    public Context context;
    public LayoutInflater inflater;

    public AppBaseAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    public class SupperViewHolder {
        public View itemView;

        public SupperViewHolder(int resLayoutId, ViewGroup parent) {
            itemView = inflater.inflate(resLayoutId, parent,false);
            itemView.setTag(this);
            
        }
    }
//    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

}
