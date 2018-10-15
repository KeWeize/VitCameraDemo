package com.vit.cameralibs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class VCameraView extends FrameLayout implements CameraOpenOverCallback, SurfaceHolder.Callback {

    private final static String TAG = VCameraView.class.getSimpleName();

    private Context mContext;
    private VideoView mVideoView;
    /**
     * 视图高宽比
     */
    private float screenProp;
    private int mWidth;
    private int mHeight;

    public VCameraView(@NonNull Context context) {
        this(context, null);
    }

    public VCameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VCameraView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setWillNotDraw(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_camera, this);
        mVideoView = view.findViewById(R.id.video_preview);
        mVideoView.getHolder().addCallback(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthSize = mVideoView.getMeasuredWidth();
        float heightSize = mVideoView.getMeasuredHeight();
        mWidth = (int) widthSize;
        mHeight = (int) heightSize;
        if (screenProp == 0) {
            screenProp = heightSize / widthSize;
            Log.d(TAG, "onMeasure: " + screenProp);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "JCameraView SurfaceCreated");
        new Thread() {
            @Override
            public void run() {
                CameraWorker.getInstance().doOpenCamera(VCameraView.this);
            }
        }.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "JCameraView SurfaceDestroyed");
        CameraWorker.getInstance().doDestroyCamera();
    }

    @Override
    public void cameraHasOpened() {
        // 相机开启回调，开启预览
        CameraWorker.getInstance().doStartPreview(mVideoView.getHolder(), screenProp, mWidth, mHeight);
    }
}
