package com.vit.scanlibs.core;

import android.view.SurfaceHolder;

/**
 * <p> 定义相机管理器需要实现的方法 <p/>
 *
 * @author kewz
 */

interface AbsCamera {

    /**
     * 遍历有效的摄像头
     */
    void findAvailableCameras();

    /**
     * 执行相机开启
     *
     * @param callback 回调对象
     */
    void doOpenCamera(CameraOpenOverCallback callback);

    /**
     * 开启相机预览
     *
     * @param holder     需要展示的 SurfaceView 对应的 SurfaceHolder
     * @param screenProp 屏幕比例，用于处理预览界面变形
     */
    void doStartPreview(SurfaceHolder holder, float screenProp, int width, int height);

    /**
     * 停止相机预览
     */
    void doStopPreview();

    /**
     * 销毁 Camera 实例
     */
    void doDestroyCamera();

}
