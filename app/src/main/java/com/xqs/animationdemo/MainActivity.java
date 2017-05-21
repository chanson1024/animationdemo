package com.xqs.animationdemo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    public CameraSurfaceView surfaceView;

    public SurfaceHolder surfaceHolder;

    public Camera camera;

    private ImageView imageView;

    private FrameLayout relativeLayout;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        initView();


        relativeLayout = (FrameLayout)findViewById(R.id.activity_main);

        view = LayoutInflater.from(this).inflate(R.layout.activity_wrap,null);

        relativeLayout.addView(view);

        imageView = (ImageView) view.findViewById(R.id.iv_img);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.move);
        imageView.startAnimation(animation);

    }

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
