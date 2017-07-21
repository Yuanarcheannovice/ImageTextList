package com.xz.list.adapter.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 一个简单封装了，Rl点击事件的类，
 * Created by xz on 2016/8/15 0015.
 */
public abstract class RvBaseAdapter extends
        RecyclerView.Adapter<RvViewHolder> {

    private RvItemListener.onItemClickListener itemClickListener;
    private RvItemListener.onLongItemClickListener longItemClickListener;

    public void setItemClickListener(RvItemListener.onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLongItemClickListener(RvItemListener.onLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View itemView = LayoutInflater.from(parent.getContext()).inflate(, parent, false);
        RvViewHolder viewHolder = RvViewHolder.createViewHolder(parent.getContext(), parent, getItemLayout(viewType));
        if (itemClickListener != null) {
            viewHolder.addRvItemListener(itemClickListener);
        }
        if (longItemClickListener != null) {
            viewHolder.addRvItemLongListener(longItemClickListener);
        }
        return viewHolder;
    }


    @LayoutRes
    public abstract int getItemLayout(int viewType);

}
