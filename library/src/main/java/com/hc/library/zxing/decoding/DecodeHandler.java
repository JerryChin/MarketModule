/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hc.library.zxing.decoding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.hc.library.R;
import com.hc.library.zxing.BaseLuminanceSource;
import com.hc.library.zxing.PlanarYUVLuminanceSource;
import com.hc.library.zxing.RGBLuminanceSource;
import com.hc.library.zxing.Recycler;
import com.hc.library.zxing.camera.CameraManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Hashtable;

final class DecodeHandler extends Handler {
    private CaptureActivity activity = null;
    private Handler handler = null;
    private final MultiFormatReader multiFormatReader;
    private int mScanTarget;

    private Handler getHandler() {
        if (handler != null) {
            return handler;
        } else {
            return activity.getHandler();
        }
    }

    DecodeHandler(CaptureActivity activity, Hashtable<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.activity = activity;
    }

    DecodeHandler(Handler handler, Hashtable<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.handler = handler;
    }

    @Override
    public void handleMessage(Message message) {
        Bundle data = message.getData();
        mScanTarget = data == null ? -1:data.getInt(CaptureActivityHandler.SCAN_TARGET,-1);

        if (message.what == R.id.decode_by_byte) {
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (message.what == R.id.decode_by_file) {
            decode((File) message.obj);
        } else if (message.what == R.id.decode_by_bitmap) {
            decode((Bitmap) message.obj);
        } else if (message.what == R.id.quit) {
            Looper looper = Looper.myLooper();
            if(looper != null){
                looper.quit();
            }
        }
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
     * reuse the same reader objects from one decode to the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(final byte[] data, final int width, final int height) {
        //modify here
        final byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }

        PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, height, width);
        decodeSource(source);
    }

    /**
     * 解码二维码图片
     */
    private void decode(final Bitmap bitmap) {
        RGBLuminanceSource source = new RGBLuminanceSource(compressImage(bitmap));
        decodeSource(source);
    }

    /**
     * 解码二维码图片
     *
     * @param file 二维码图片文件
     */
    private void decode(final File file) {
        if (file == null) {
            decodeSource(null);
            return;
        }
        if (!file.isFile()) {
            decodeSource(null);
            return;
        }

        Bitmap bitmap = compressImage(file);

        if (bitmap == null) {
            if(getHandler() != null){
                getHandler().obtainMessage(R.id.decode_failed).sendToTarget();
            }
            return;
        }

        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        decodeSource(source);
    }

    private Bitmap compressImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,outputStream);

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        BitmapFactory.Options options = new BitmapFactory.Options();
        //获得图片要缩放的比例
        options.inSampleSize = (int) ((height > width ? height : width)
                        / 400f);

        Bitmap bp = BitmapFactory.decodeByteArray(outputStream.toByteArray(),0,outputStream.size(),options);

        if(bitmap.isRecycled()){
           bitmap.recycle();
        }

        return bp;
    }


    private Bitmap compressImage(File file){
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只获取图片的尺寸
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        //获得图片要缩放的比例
        int simpleSize = (int) ((options.outHeight > options.outWidth ? options.outHeight : options.outWidth)
                / 400f);
        simpleSize = simpleSize > 0 ? simpleSize : 1;
        options.inSampleSize = simpleSize;
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(file.getPath(), options);

        return bitmap;
    }

    private synchronized void decodeSource(BaseLuminanceSource source) {
        if (source == null) {
            multiFormatReader.reset();
            sendFailedMsg();
            return;
        }

        Result rawResult = null;
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            rawResult = multiFormatReader.decodeWithState(bitmap);
        } catch (ReaderException re) {
            re.printStackTrace();
        } finally {
            multiFormatReader.reset();
        }

        if (rawResult != null) {
            Message message = Message.obtain(getHandler(), R.id.decode_succeeded, rawResult);
            Bundle bundle = new Bundle();
            if(mScanTarget != -1){
                bundle.putInt(CaptureActivityHandler.SCAN_TARGET,mScanTarget);
            }
            try {
                Bitmap b = source.renderCroppedGreyscaleBitmap();
                if (b != null) {
                    bundle.putParcelable(DecodeThread.BARCODE_BITMAP, b);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            message.setData(bundle);
            //Log.d(TAG, "Sending decode succeeded message...");
            message.sendToTarget();
        } else {
            if(source instanceof Recycler){
                ((Recycler)source).recycle();
            }
            sendFailedMsg();
        }
    }

    private void sendFailedMsg(){
        if(getHandler() != null) {
            Message message = Message.obtain(getHandler(), R.id.decode_failed);
            if(mScanTarget != -1){
                Bundle bundle = new Bundle();
                bundle.putInt(CaptureActivityHandler.SCAN_TARGET,mScanTarget);
                message.setData(bundle);
            }
            message.sendToTarget();
        }
    }

}
