package com.vit.scanlibs.core;

import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import com.vit.cameralibs.utils.CameraParamUtil;
import com.vit.cameralibs.utils.CheckPermission;

import java.io.IOException;


/**
 * <p> 实现相机管理器 <p/>
 *
 * @author kewz
 */

public class CameraWorker implements AbsCamera, Camera.PreviewCallback {

    private static final String TAG = CameraWorker.class.getSimpleName();
    private static final CameraWorker ourInstance = new CameraWorker();

    public static CameraWorker getInstance() {
        return ourInstance;
    }

    private Camera mCamera;
    private Camera.Parameters mCameraPamera;
    private boolean isPreviewing = false;
    /**
     * 记录后置摄像头位置
     */
    private int CAMERA_POST_POSTION = -1;
    private int mSelectedCamera = -1;

    /**
     * 摄像头角度  默认为 90
     */
    private int mCameraAngle = 90;//摄像头角度   默认为90度

    private SurfaceHolder mHolder = null;
    private float mScreenProp = -1.0f;

    private CameraWorker() {
        // 遍历有效摄像头，获取后置摄像头位置
        findAvailableCameras();
        mSelectedCamera = CAMERA_POST_POSTION;
    }

    @Override
    public void findAvailableCameras() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        int cameraNum = Camera.getNumberOfCameras();
        for (int i = 0; i < cameraNum; i++) {
            // 遍历摄像头信息，获取后置摄像头位置
            switch (info.facing) {
                case Camera.CameraInfo.CAMERA_FACING_BACK:
                    CAMERA_POST_POSTION = info.facing;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void doOpenCamera(CameraOpenOverCallback callback) {
        if (mCamera == null) {
            openCamera(mSelectedCamera);
        }
        // 摄像头打开
        callback.cameraHasOpened();
    }

    public void openCamera(int id) {
        try {
            this.mCamera = Camera.open(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doStartPreview(SurfaceHolder holder, float screenProp, int width, int height) {
        if (isPreviewing) {
            // 正在预览
            Log.d(TAG, "doStartPreview: isPreviewing");
        }
        if (holder == null) {
            return;
        }
        if (this.mScreenProp < 0) {
            this.mScreenProp = screenProp;
            Log.d(TAG, "doStartPreview: " + screenProp);
        }
        this.mHolder = holder;
        if (mCamera != null) {
            try {
                mCameraPamera = mCamera.getParameters();
                // 获取最佳预览尺寸

//                Camera.Size previewSize = CameraParamUtil.getInstance()
//                        .getPreviewSize(mCameraPamera.getSupportedPreviewSizes(), 800, screenProp);

                Point point = new Point(width, height);

                if (height < width){
                    point = new Point(height, width);
                }
                Log.d(TAG, "doStartPreview: w:" + width + "h:" + height);
                Point mPreviewSize = CameraParamUtil.getInstance()
                        .getBestPreviewSize(mCameraPamera.getSupportedPreviewSizes(), point);


                Camera.Size pictureSize = CameraParamUtil.getInstance()
                        .getPictureSize(mCameraPamera.getSupportedPictureSizes(), 1200, screenProp);
                // 设置预览尺寸
                mCameraPamera.setPreviewSize(mPreviewSize.x, mPreviewSize.y);
                // 设置图片尺寸
                mCameraPamera.setPictureSize(pictureSize.width, pictureSize.height);
                if (CameraParamUtil.getInstance().isSupportedFocusMode(
                        mCameraPamera.getSupportedFocusModes(),
                        Camera.Parameters.FOCUS_MODE_AUTO)) {
                    mCameraPamera.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }
                if (CameraParamUtil.getInstance().isSupportedPictureFormats(mCameraPamera.getSupportedPictureFormats(),
                        ImageFormat.JPEG)) {
                    mCameraPamera.setPictureFormat(ImageFormat.JPEG);
                    mCameraPamera.setJpegQuality(100);
                }
                mCamera.setParameters(mCameraPamera);
                mCameraPamera = mCamera.getParameters();
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(mCameraAngle);
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
                isPreviewing = true;
                Log.d(TAG, "=== Start Preview ===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doStopPreview() {
        if (null != mCamera) {
            try {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                //这句要在stopPreview后执行，不然会卡顿或者花屏
                mCamera.setPreviewDisplay(null);
                isPreviewing = false;
                Log.i(TAG, "=== Stop Preview ===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doDestroyCamera() {
        if (null != mCamera) {
            try {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                //这句要在stopPreview后执行，不然会卡顿或者花屏
                mCamera.setPreviewDisplay(null);
                mHolder = null;
                isPreviewing = false;
                mCamera.release();
                mCamera = null;
                Log.i(TAG, "=== Destroy Camera ===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "=== Camera  Null===");
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        // 预览回调
    }
}
