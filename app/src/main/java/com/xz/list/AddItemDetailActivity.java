package com.xz.list;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.xz.list.adapter.AddDetailAdapter;
import com.xz.list.app.AppPathManager;
import com.xz.list.entity.ItemDetailEntity;
import com.xz.list.utils.AppStatic;
import com.xz.list.utils.ToastUtil;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by xz on 2017/7/19 0019.
 * 添加item- 图文详细
 * <p>
 * 原理:  把一个Edittext和一个ImageView 作为一个Item, 每添加一张图片,相当于在把最后一个item的值填充完，
 * 然后再添加一个新的  item，
 * <p>
 * 缺陷： 无法在中间插入
 * <p>
 * 优点：可以对图片进行修改，裁剪，比 一般在Edittext做图文混排，比较贴近用户操作
 * <p>
 * 暂未对字作处理(预留),只有图片处理可用
 */
public class AddItemDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox mBoldCB;
    private CheckBox mColorCB;
    private CheckBox mUnderlineCB;
    private CheckBox mTextsizeCB;
    private CheckBox mImgCB;
    private AddDetailAdapter mAddDetailAdapter;
    private int mTagPosition = -1;//需要修改的item的position


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
    }

    private void initView() {
        findViewById(R.id.add_detail_submit).setOnClickListener(this);//确认

        findViewById(R.id.add_detail_back).setOnClickListener(this);//返回

        //加粗
        mBoldCB = (CheckBox) findViewById(R.id.a_d_bold);
        mBoldCB.setOnClickListener(this);

        //修改颜色
        mColorCB = (CheckBox) findViewById(R.id.a_d_color);
        mColorCB.setOnClickListener(this);

        //下划线
        mUnderlineCB = (CheckBox) findViewById(R.id.a_d_underline);
        mUnderlineCB.setOnClickListener(this);

        //字体大小
        mTextsizeCB = (CheckBox) findViewById(R.id.a_d_textsize);
        mTextsizeCB.setOnClickListener(this);

        //选择图片
        mImgCB = (CheckBox) findViewById(R.id.a_d_img);
        mImgCB.setOnClickListener(this);

        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.a_d_rv);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAddDetailAdapter = new AddDetailAdapter(this);
        recyclerview.setAdapter(mAddDetailAdapter);

        //添加第一次数据
        addItemData();
    }

    /**
     * 添加一个新的Item
     */
    private void addItemData() {
        ItemDetailEntity ide = new ItemDetailEntity();
        ide.setxContent("");
        ide.setShowImg(false);
        List<ItemDetailEntity> listIDE = new ArrayList<>();
        listIDE.add(ide);
        mAddDetailAdapter.addData(listIDE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_detail_back:
                this.finish();
                break;
            case R.id.add_detail_submit:
                //添加确认
                if (mAddDetailAdapter != null) {
                    List<ItemDetailEntity> listIDE = mAddDetailAdapter.getData();
                    ArrayList<ItemDetailEntity> arrayList = new ArrayList<>(listIDE);
                    Intent intent = new Intent();
                    intent.putExtra(AppStatic.ADD_DETAIL_STR, arrayList);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.a_d_bold:
                //加粗
                break;
            case R.id.a_d_color:
                //设置颜色
                break;
            case R.id.a_d_underline:
                //设置下划线
                break;
            case R.id.a_d_textsize:
                //设置字体大小
                break;
            case R.id.a_d_img:
                //添加图片 是添加在最后一个item上面的
                PhotoPicker.builder()
                        .setGridColumnCount(4)
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(true)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
        }
    }

    /**
     * 修改图片
     * 给adapter调用
     */
    public void updateImg(int position) {
        mTagPosition = position;
        ItemDetailEntity ide = mAddDetailAdapter.getItem(position);

        if (ide.getxImageUrl() != null) {
            //跳转裁剪
            UCrop.of(Uri.parse(ide.getxImageUrl()), Uri.parse(AppPathManager.getSaveImageUrl() + AppPathManager.getImageName()))
                    .start(this);
        }

    }

    /**
     * 替换图片
     * 给adapter调用
     */
    public void replaceImg(int position) {
        mTagPosition = position;
        //添加图片 是添加在最后一个item上面的
        PhotoPicker.builder()
                .setGridColumnCount(4)
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(true)
                .start(this, AppStatic.PHOTO_CODE_REPLACE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoPicker.REQUEST_CODE:
                //添加图片
                if (data != null && resultCode == Activity.RESULT_OK) {
                    ArrayList<String> coverPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (coverPhotos.size() > 0) {
                        //获取最后一个,添加照片，并显示
                        ItemDetailEntity ide = mAddDetailAdapter.getItem(mAddDetailAdapter.getItemCount() - 1);
                        ide.setShowImg(true);
                        ide.setxImageUrl("file://" + coverPhotos.get(0));
                        mAddDetailAdapter.notifyItemRangeChanged(mAddDetailAdapter.getItemCount() - 1, 1);
                        //再添加一个新的item
                        addItemData();
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                //裁剪图片
                if (resultCode == RESULT_OK) {
                    if (mTagPosition != -1) {
                        Uri resultUri = UCrop.getOutput(data);
                        //刷新修改的item
                        mAddDetailAdapter.getItem(mTagPosition).setxImageUrl(resultUri.getPath());
                        mAddDetailAdapter.notifyItemRangeChanged(mTagPosition, 1);
                    }
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    //  Throwable cropError = UCrop.getError(data);
                    ToastUtil.show("裁剪发生错误,请重试");
                }
                break;
            case AppStatic.PHOTO_CODE_REPLACE:
                //替换图片
                if (data != null && resultCode == Activity.RESULT_OK) {
                    ArrayList<String> coverPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (coverPhotos.size() > 0) {
                        mAddDetailAdapter.getItem(mTagPosition).setxImageUrl("file://" + coverPhotos.get(0));
                        mAddDetailAdapter.notifyItemRangeChanged(mTagPosition, 1);
                    }
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        if (mAddDetailAdapter != null) {
            mAddDetailAdapter.getData().clear();
            mAddDetailAdapter = null;
        }
    }
}
