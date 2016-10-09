package com.hc.library.zxing.decoding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.hc.library.R;
import com.hc.library.util.L;
import com.hc.library.zxing.ViewfinderView;
import com.hc.library.zxing.camera.CameraManager;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public abstract class CaptureActivity extends Activity implements Callback {
    private final static int VIBRATOR_MILLI_SECONDS = 200;

    public final static int REQUEST_GET_IMAGE = 2;

    private CaptureActivityHandler mCaptureActivityHandler;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;
    private ProgressBar mPbDistinguishing;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet = "UTF-8";
    /**
     * 一个定时器
     */
    private InactivityTimer inactivityTimer;
    /**
     * 选择相册图片
     */
    private Button mBtnScanPic;
    private CheckBox mCbToggleLight;
    private Button mBtnMyQRCode;
    private View mIBtnBack;

    private Vibrator mVibrator;

    private boolean mIsActivityResult = false;


    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btn_back) {
                CaptureActivity.this.onBackPressed();
            } else if (i == R.id.btn_scan_pic) {
                onSelectingCodeImageOnClick();//选择二维码图片
            } else if (i == R.id.btn_light) {//控制闪光灯
                mCbToggleLight.setChecked(CameraManager.get().toggleFlashlightEnabled());
            } else if (i == R.id.btn_qr_code) {
                onBuildOwnQRCode();//生成自己的二维码
            }
        }
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置屏幕只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);

        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_capture);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplicationContext());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.i("way","onResume");

        if(!mIsActivityResult) {
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            if (hasSurface) {
                initCamera(surfaceHolder);
            } else {
                surfaceHolder.addCallback(this);
            }
        }else{
            mIsActivityResult = false;
        }

        if(mCbToggleLight.isChecked()){
            CameraManager.get().openFlashLigth();
        }else{
            CameraManager.get().closeFlashLigth();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCaptureActivityHandler != null) {
            mCaptureActivityHandler.quitSynchronously();
            mCaptureActivityHandler = null;
        }
        CameraManager.get().closeDriver();
    }


    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("way","onActivityResult");

        switch (requestCode) {
            case REQUEST_GET_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bitmap image = null;
                        //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                        Uri mImageCaptureUri = data.getData();
                        //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                        if (mImageCaptureUri != null) {
                            try {
                                //这个方法是根据Uri获取Bitmap图片的静态方法
                                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Bundle extras = data.getExtras();
                            if (extras != null) {
                                //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                                image = extras.getParcelable("data");
                            }
                        }
                        if (image != null) {
                            loading();

                            Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
                            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
                            initCamera(surfaceView.getHolder());
                            mIsActivityResult = true;
                            mCaptureActivityHandler.restartDecodeBitmap(image);
                        }
                    }
                }
                break;
        }
    }


    private void initView() {
        mIBtnBack = findViewById(R.id.btn_back);
        mBtnScanPic = (Button) findViewById(R.id.btn_scan_pic);
        mCbToggleLight = (CheckBox) findViewById(R.id.btn_light);
        mBtnMyQRCode = (Button) findViewById(R.id.btn_qr_code);
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);

        mIBtnBack.setOnClickListener(mClickListener);
        mBtnScanPic.setOnClickListener(mClickListener);
        mCbToggleLight.setOnClickListener(mClickListener);
        mBtnMyQRCode.setOnClickListener(mClickListener);
        mPbDistinguishing = (ProgressBar) findViewById(R.id.pb_distinguishing);
    }


    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    protected final void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        mVibrator.vibrate(VIBRATOR_MILLI_SECONDS);
        if (onHandleDecode(result, barcode)) {
            if (barcode != null && !barcode.isRecycled()) {
                barcode.recycle();
            }
        }
    }

    /**
     * 处理二维码的结果
     *
     * @return 是否处理完成
     */
    protected abstract boolean onHandleDecode(Result result, Bitmap barcode);

    /**
     * 生成自己的二维码
     */
    protected abstract void onBuildOwnQRCode();


    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException | RuntimeException ioe) {
            return;
        }

        if (mCaptureActivityHandler == null) {
            mCaptureActivityHandler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
            mCbToggleLight.setEnabled(CameraManager.get().isCanOpenFlashlight());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public final ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public final CaptureActivityHandler getHandler() {
        return mCaptureActivityHandler;
    }

    public final void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    private void loading(){
        mPbDistinguishing.setVisibility(View.VISIBLE);
        viewfinderView.showLoading();
        mBtnScanPic.setEnabled(false);
    }

    private void unLoading(){
        mPbDistinguishing.setVisibility(View.GONE);
        viewfinderView.hideLoading();
        mBtnScanPic.setEnabled(true);
    }

    final void decodeImageFileComplete(boolean isSuccess){
        unLoading();
        onDecodeImageFileComplete(isSuccess);
    }

    /**
     * 二维码图片解析完成
     *
     * @param isSuccess 是否解析成功
     */
    protected void onDecodeImageFileComplete(boolean isSuccess) {
    }


    /**
     * 点击选择二维码图片时发生的事件，可以被重写
     * 重写该方法时要重写onActivityResult方法
     */
    protected void onSelectingCodeImageOnClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GET_IMAGE);
    }

}