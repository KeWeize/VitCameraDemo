package com.vit.scanlibs;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * <p> 用于 app 项目传入全局 context 初始化屏幕信息 <p/>
 *
 * @author kewz
 */

public class ZxingScanLibs {

    /**
     * 屏幕宽度
     */
    public static int screenWidthPx;
    /**
     * 屏幕高度
     */
    public static int screenhightPx;
    /**
     * 密度
     */
    public static float density;
    /**
     * dpi
     */
    public static int densityDPI;
    /**
     * 屏幕宽度（dp）
     */
    public static float screenWidthDip;
    /**
     * 屏幕高度（dp）
     */
    public static float screenHightDip;

    public static void init(Context mContext) {
        if (mContext == null) {
            return ;
        }
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        ZxingScanLibs.density = dm.density;
        ZxingScanLibs.densityDPI = dm.densityDpi;
        ZxingScanLibs.screenWidthPx = dm.widthPixels;
        ZxingScanLibs.screenhightPx = dm.heightPixels;
        ZxingScanLibs.screenWidthDip = px2dip(mContext, dm.widthPixels);
        ZxingScanLibs.screenHightDip = px2dip(mContext, dm.heightPixels);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
