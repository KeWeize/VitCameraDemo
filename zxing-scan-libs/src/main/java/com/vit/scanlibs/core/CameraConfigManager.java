package com.vit.scanlibs.core;

import android.content.Context;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class CameraConfigManager {

    private static final String TAG = CameraConfigManager.class.getSimpleName();

    private static CameraConfigManager ourInstance;

    private CameraConfigManager() {
    }

    public static CameraConfigManager getInstance() {
        if (ourInstance == null) {
            synchronized (CameraConfigManager.class) {
                if (ourInstance == null) {
                    ourInstance = new CameraConfigManager();
                }
            }
        }
        return ourInstance;
    }



}
