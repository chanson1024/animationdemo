package com.xqs.animationdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2017/5/21 0021.
 */


public class CameraSurfaceView extends SurfaceView {

    private static final String TAG = "CameraSurfaceView";
    SurfaceHolder mSurfaceHolder;
    public float rectLeft, rectTop, rectRight, rectBottom;
    private int mScreenHeight, mScreenWidth;
    Paint rectPaint;
    private android.graphics.Xfermode xfermode_XOR = new PorterDuffXfermode(
            PorterDuff.Mode.XOR);

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        mSurfaceHolder.addCallback(this);



    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.w(TAG,"onDraw=============");


    }

}