package com.hc.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Xiong on 2016/9/19.
 */
public final class Assets {
    private final static int BUFFER_SIZE = 1024 * 2;

    private Assets() {
        throw new RuntimeException("Stub");
    }

    static {
        System.loadLibrary("flzx-jni");
    }


    public native static byte[] decode(byte[]bytes);

    public static byte[] getResourceByteArray(Context context, String fileName){
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            is = context.getAssets().open(fileName);
            int len;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = is.read(buffer)) != 0) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            byte[] bytes = baos.toByteArray();
            bytes = decode(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static Bitmap getBitmap(Context context, String fileName) {
        byte[] bytes = getResourceByteArray(context,fileName);
        return BitmapDecodeUtils.decodeByteArray(context,bytes);
    }

    public InputStream getInputStream(Context context,String fileName){
        byte[]bytes = getResourceByteArray(context,fileName);
        return new ByteArrayInputStream(bytes);
    }
}
