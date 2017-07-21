package com.xz.list.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xz.list.R;
import com.xz.list.adapter.recyclerview.RvDataAdapter;
import com.xz.list.adapter.recyclerview.RvViewHolder;
import com.xz.list.entity.ItemDetailEntity;

/**
 * Created by xz on 2017/7/20 0020.
 */

public class MainDetailAdapter extends RvDataAdapter<ItemDetailEntity> {
    private Context mContext;

    public MainDetailAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_main_detail;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        ItemDetailEntity ide = mdata.get(position);

        ImageView iv = holder.getView(R.id.i_m_d_iv);
        TextView tv = holder.getView(R.id.i_m_d_tv);

        tv.setText(ide.getxContent());

        if (!TextUtils.isEmpty(ide.getxImageUrl())) {
            Glide.with(mContext).load(ide.getxImageUrl()).fitCenter().into(iv);
        } else {
            iv.setVisibility(View.GONE);
        }

    }
}
