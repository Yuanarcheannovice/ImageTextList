package com.xz.list;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xz.list.app.AppPathManager;
import com.xz.list.entity.ItemDetailEntity;
import com.xz.list.entity.ItemEntity;
import com.xz.list.utils.AppStatic;
import com.xz.list.utils.DateTimeUtil;
import com.xz.list.utils.ToastUtil;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by xz on 2017/7/19 0019.
 * 添加item
 */

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mAddIv;
    private EditText mAddTitle;
    private EditText mAddContent;
    private Uri mImageUri;
    private ArrayList<ItemDetailEntity> mListIDE;//图文混排返回集合


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initView();
    }

    private void initView() {
        findViewById(R.id.add_back).setOnClickListener(this);

        findViewById(R.id.add_submit).setOnClickListener(this);

        findViewById(R.id.add_image_layout).setOnClickListener(this);

        findViewById(R.id.add_detail_layout).setOnClickListener(this);

        mAddIv = (ImageView) findViewById(R.id.add_iv);

        mAddTitle = (EditText) findViewById(R.id.add_title);

        mAddContent = (EditText) findViewById(R.id.add_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_back:
                this.finish();
                break;
            case R.id.add_submit:
                //确认
                doSubmit();
                break;
            case R.id.add_image_layout:
                //添加图片
                PhotoPicker.builder()
                        .setGridColumnCount(4)
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(true)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.add_detail_layout:
                //图文混排
                Intent intent = new Intent(this, AddItemDetailActivity.class);
                startActivityForResult(intent, AppStatic.ADD_DETAIL_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoPicker.REQUEST_CODE:
                //选择图片
                if (data != null && resultCode == Activity.RESULT_OK) {
                    ArrayList<String> coverPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (coverPhotos.size() > 0) {
                        //跳转裁剪
                        UCrop.of(Uri.parse("file://" + coverPhotos.get(0)), Uri.parse(AppPathManager.getSaveImageUrl() + AppPathManager.getImageName()))
                                .withAspectRatio(16, 9)
                                .withMaxResultSize(640, 360)
                                .start(this);
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    mImageUri = UCrop.getOutput(data);
                    Glide.with(this).load(mImageUri).into(mAddIv);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    Throwable cropError = UCrop.getError(data);
                    ToastUtil.show("裁剪发生错误,请重试");
                }
                break;
            case AppStatic.ADD_DETAIL_CODE:
                if (resultCode == RESULT_OK) {
                    mListIDE = (ArrayList<ItemDetailEntity>) data.getSerializableExtra(AppStatic.ADD_DETAIL_STR);
                }
                break;
        }
    }

    /**
     * 确认数据
     */
    private void doSubmit() {
        String title = mAddTitle.getText().toString();
        String content = mAddContent.getText().toString();
        if (mImageUri == null) {
            ToastUtil.show("请选择图片");
            return;
        }
        if (TextUtils.isEmpty(title)) {
            ToastUtil.show("请输入Title");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show("请输入content");
            return;
        }
        if (mListIDE == null || mListIDE.size() == 0) {
            ToastUtil.show("去图文混排一下把");
            return;
        }

        ItemEntity ie = new ItemEntity();
        ie.setImageUrl(mImageUri.getPath());
        ie.setTitle(title);
        ie.setContent(content);
        ie.setAddTiem(DateTimeUtil.formatDate(new Date(), "HH:mm"));
        ie.setListIDE(mListIDE);
        EventBus.getDefault().post(ie);
        finish();
    }
}
