package com.xz.list.adapter.recyclerview;

import android.support.annotation.LayoutRes;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装对数据操作
 * Created by xz on 2016/8/16 0016.
 */
public abstract class RvDataAdapter<D> extends RvBaseAdapter {

    protected List<D> mdata = new ArrayList<>();

    @LayoutRes
    @Override
    public abstract int getItemLayout(int viewType);

    @Override
    public abstract void onBindViewHolder(RvViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (mdata != null) {
            return mdata.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * 数据替换，不带刷新
     */
    public void setData(List<D> data) {
        this.mdata = data;
    }

    /**
     * 数据替换 ，带刷新
     *
     * @param data      数据
     * @param isRefresh 是否刷新
     */
    public void setData(List<D> data, boolean isRefresh) {
        this.mdata = data;
        if (isRefresh)
            notifyDataSetChanged();
    }

    /**
     * 数据集合增加数据 不带刷新
     *
     * @param data 数据
     */
    public void addData(List<D> data) {
        if (this.mdata == null) {
            this.mdata = new ArrayList<>();
        }
        this.mdata.addAll(data);
    }

    /**
     * 数据集合增加数据 带刷新
     *
     * @param data      数据
     * @param isRefresh 是否刷新
     */
    public void addData(List<D> data, boolean isRefresh) {
        if (this.mdata == null) {
            this.mdata = new ArrayList<>();
            this.mdata.addAll(data);
            if (isRefresh)
                notifyDataSetChanged();
        } else {
            this.mdata.addAll(data);
            //因为加载栏，刷新时的条目要多一栏
            notifyItemRangeInserted(mdata.size() - data.size(), data.size());
        }
    }

    public boolean removeData(D data) {
        return this.mdata != null && this.mdata.remove(data);
    }

    public List<D> getData() {
        if (mdata == null)
            return new ArrayList<>();
        else
            return this.mdata;
    }

    public D getItem(int position) {
        if (mdata != null && !mdata.isEmpty()) {
            return mdata.get(position);
        }
        return null;
    }
}
