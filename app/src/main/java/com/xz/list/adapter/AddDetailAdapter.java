package com.xz.list.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xz.list.AddItemDetailActivity;
import com.xz.list.R;
import com.xz.list.adapter.recyclerview.RvDataAdapter;
import com.xz.list.adapter.recyclerview.RvViewHolder;
import com.xz.list.entity.ItemDetailEntity;

/**
 * Created by xz on 2017/7/20 0020.
 * 图文混排的适配器
 */

public class AddDetailAdapter extends RvDataAdapter<ItemDetailEntity> {


    private AddItemDetailActivity mActivity;

    public AddDetailAdapter(AddItemDetailActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_detail;
    }

    @Override
    public int getItemViewType(int position) {
        //设置position不同，可以让RecyclerView，不复用View，使Edittext更好处理
        return position;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, final int position) {
        final ItemDetailEntity ide = mdata.get(position);
        ide.setxPosition(position);

        View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.i_d_delete:
                        //删除图片
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                        builder.setTitle("提示")
                                .setMessage("是否删除此图?")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //如果要执行删除图片操作，需要把单前的实体删除，
                                        // 并把已经输入的文字，放到下一个实体的开始
                                        ItemDetailEntity idethis = mdata.get(position);//当前实体
                                        ItemDetailEntity ideNext = mdata.get(position + 1);//下个实体12345
                                        //文字给与
                                        if (!TextUtils.isEmpty(idethis.getxContent())) {
                                            ideNext.setxContent(idethis.getxContent() + "\n" + ideNext.getxContent());
                                        }
                                        mdata.remove(position);
//                                        notifyItemRemoved(position);
                                        notifyDataSetChanged();
                                    }
                                }).show();
                        break;
                    case R.id.i_d_replace:
                        //替换图片
                        mActivity.replaceImg(position);
                        break;
                    case R.id.i_d_update:
                        //修改图片
                        mActivity.updateImg(position);
                        break;
                }
            }
        };
        //输入栏
        EditText editText = holder.getView(R.id.i_d_et);

        //操作栏
        final RelativeLayout operateLayout = holder.getView(R.id.i_d_rl);

        //图片
        final ImageView imageView = holder.getView(R.id.i_d_iv);

        //填充栏
        View fillView = holder.getView(R.id.i_d_fill);

        //删除按钮
        holder.getView(R.id.i_d_delete).setOnClickListener(onClick);

        //替换按钮
        holder.getView(R.id.i_d_replace).setOnClickListener(onClick);

        //修改按钮
        holder.getView(R.id.i_d_update).setOnClickListener(onClick);

        //////-------文本框处理
        if(editText.getTag() instanceof TextWatcher){
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
        MyTextWatcher myTextWatcher = new MyTextWatcher(ide);
            editText.addTextChangedListener(myTextWatcher);
        editText.setTag(myTextWatcher);
//
//        if (mTextWatcherMap.get(ide.getxPosition()) == null) {
//
////            mTextWatcherMap.put(ide.getxPosition(), myTextWatcher);
////        } else {
////            myTextWatcher = mTextWatcherMap.get(position);
//        }
////
////        editText.setTag(myTextWatcher);

        if (ide.getxContent() != null) {
            editText.setText(ide.getxContent());
            editText.setSelection(editText.getText().length());
        }


        //图片和操作栏
        if (ide.isShowImg()) {
            Glide.with(mActivity).load(ide.getxImageUrl()).fitCenter().into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                    operateLayout.setVisibility(View.VISIBLE);
                }
            });
        } else {
            operateLayout.setVisibility(View.GONE);
        }

        //添加填充区域的
        if (mdata.size() - 1 == position) {
            fillView.setVisibility(View.VISIBLE);
        } else fillView.setVisibility(View.GONE);
    }

    private class MyTextWatcher implements TextWatcher {
        private ItemDetailEntity mItemDetailEntity;

        MyTextWatcher(ItemDetailEntity itemDetailEntity) {
            this.mItemDetailEntity = itemDetailEntity;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
        }

        @Override
        public void afterTextChanged(Editable s) {
            //s:变化后的所有字符
            mItemDetailEntity.setxContent(s.toString());
        }
    }

}
