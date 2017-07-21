package com.xz.list.utils;

import android.widget.Toast;

import com.xz.list.app.AppApplication;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class ToastUtil {

    public static void show(String str){
        Toast.makeText(AppApplication.context,str,Toast.LENGTH_SHORT).show();
    }
}
