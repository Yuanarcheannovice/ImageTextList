package com.xz.list.adapter.recyclerview;

import android.view.View;

/**
 * RecyclerView的条目事件
 * Created by xz on 2016/9/9 0009.
 */
public class RvItemListener {
    public interface onItemClickListener {
         void onItemClickListener(View view, int position);
    }

    public interface onLongItemClickListener {
         void onLongItemClickListener(View view, int position);
    }
}
