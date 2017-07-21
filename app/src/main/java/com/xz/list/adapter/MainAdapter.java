package com.xz.list.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xz.list.R;
import com.xz.list.adapter.recyclerview.RvDataAdapter;
import com.xz.list.adapter.recyclerview.RvViewHolder;
import com.xz.list.entity.ItemEntity;

/**
 * Created by xz on 2017/7/19 0019.
 */

public class MainAdapter extends RvDataAdapter<ItemEntity> {
    private Context mContext;

    public MainAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_main;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {

        ItemEntity ie = mdata.get(position);

        ImageView iv = holder.getView(R.id.i_m_iv);

        holder.setText(R.id.i_m_title, ie.getTitle());

        holder.setText(R.id.i_m_content, ie.getContent());

        holder.setText(R.id.i_m_time, ie.getAddTiem());

        Glide.with(mContext).load(ie.getImageUrl()).into(iv);

    }
}
