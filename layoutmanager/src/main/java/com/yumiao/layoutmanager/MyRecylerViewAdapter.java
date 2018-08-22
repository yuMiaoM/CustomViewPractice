package com.yumiao.layoutmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyRecylerViewAdapter extends RecyclerView.Adapter<MyRecylerViewAdapter.ViewHolder> {
    List<String> mList;
    Context mContext;

    public MyRecylerViewAdapter(Context context, List<String> list){
        this.mList=list;
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv.setText(mList.get(position));
        holder.tv2.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mList.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        TextView tv2;
        public ViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv2=itemView.findViewById(R.id.tv2);
        }
    }
}
