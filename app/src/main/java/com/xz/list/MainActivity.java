package com.xz.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xz.list.adapter.MainAdapter;
import com.xz.list.adapter.recyclerview.RvItemListener;
import com.xz.list.entity.ItemEntity;
import com.xz.list.utils.AppStatic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.main_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mMainAdapter = new MainAdapter(this);
        mMainAdapter.setItemClickListener(new RvItemListener.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                ItemEntity itemEntity = mMainAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, MainDetailActivity.class);
                intent.putExtra(AppStatic.DETAIL_STR, itemEntity);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mMainAdapter);
    }


    private void initData() {

    }

    /**
     * 刷新数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(ItemEntity itemEntity) {
        if (itemEntity != null) {
            List<ItemEntity> list = new ArrayList<>();
            list.add(itemEntity);
            mMainAdapter.addData(list, true);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
