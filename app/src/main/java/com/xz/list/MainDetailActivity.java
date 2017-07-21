package com.xz.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xz.list.adapter.MainDetailAdapter;
import com.xz.list.entity.ItemEntity;

/**
 * Created by xz on 2017/7/20 0020.
 */

public class MainDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private MainDetailAdapter mMainDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);
        initView();
        initData();
    }

    private void initData() {
        if (getIntent() != null) {
            ItemEntity itemEntity = (ItemEntity) getIntent().getSerializableExtra("DETAIL_STR");
            if (itemEntity == null)
                return;
            mMainDetailAdapter.setData(itemEntity.getListIDE());
        }
    }

    private void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        RecyclerView detailRv = (RecyclerView) findViewById(R.id.detail_rv);
        detailRv.setLayoutManager(new LinearLayoutManager(this));
        mMainDetailAdapter = new MainDetailAdapter(this);
        detailRv.setAdapter(mMainDetailAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
    }
}
