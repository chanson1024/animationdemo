package com.xqs.animationdemo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    public CameraSurfaceView surfaceView;

    public SurfaceHolder surfaceHolder;

    public Camera camera;

    private ImageView imageView;

    private FrameLayout frameLayout;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        initView();

        // 1. 看相机配置  2.看framelayout相关 3.代码顺序 4.单例搞的鬼？


        frameLayout = (FrameLayout)findViewById(R.id.activity_main);

        PopManager.getInstance().init(frameLayout,this);

        handler.sendEmptyMessageDelayed(0,2000);


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PopManager.getInstance().add();

            handler.sendEmptyMessageDelayed(0,2000);
        }
    };


    private void initView() {
        surfaceView=(CameraSurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceView.setWillNotDraw(false);
        surfaceHolder=holder;
        initCamera();
    }

    private void initCamera() {
        if (null == camera) {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            camera.setOneShotPreviewCallback(this);
        }
        if (true) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                Point p = DisplayUtil.getBestCameraResolution(parameters, ScreenUtils.getScreenMetrics(this));
                parameters.setPreviewSize(p.x, p.y);

                List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
                int fps = supportedPreviewFpsRange.get(supportedPreviewFpsRange.size() - 1)[1];
                parameters.setPreviewFpsRange(fps, fps);
                if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait");
                    parameters.set("rotation", 90);
                    camera.setDisplayOrientation(90);
                } else {
                    parameters.set("orientation", "landscape");
                    camera.setDisplayOrientation(0);
                }
                camera.setParameters(parameters);
                camera.setPreviewDisplay(surfaceHolder);
                camera.setPreviewCallback(this);
                camera.startPreview();
                camera.autoFocus(null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2) {
        surfaceHolder=holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            holder.removeCallback(this);
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        surfaceView = null;
        surfaceHolder = null;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

    }
}
