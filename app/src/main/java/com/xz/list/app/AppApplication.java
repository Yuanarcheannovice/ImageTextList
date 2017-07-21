package com.xz.list.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by xz on 2017/7/20 0020.
 */

public class AppApplication extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        AppPathManager.initPathManager("ImageTextList");
    }
}
