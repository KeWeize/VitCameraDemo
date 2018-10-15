package com.vit.vitcamerademo;

import android.app.Application;

import com.vit.scanlibs.ZxingScanLibs;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 ZxingScanLibs 信息
        ZxingScanLibs.init(getApplicationContext());
    }
}
