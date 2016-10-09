package com.hc.library.module;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by xc on 2016/8/5.
 */
public class Message {
    public int what;
    public int targetIndex;

    public Message(int what,int targetIndex){
        this.what = what;
        this.targetIndex = targetIndex;
    }

    private Bundle mData;

    public void putInt(String key, int value){
        getBundle().putInt(key,value);
    }

    public void putString(String key,String value){
        getBundle().putString(key,value);
    }

    public void putAll(Bundle bundle){
        getBundle().putAll(bundle);
    }

    public void putParcelable(String key,Parcelable value){
        getBundle().putParcelable(key,value);
    }

    public Bundle getData(){
        return mData;
    }

    private Bundle getBundle(){
        if(mData == null){
            mData = new Bundle();
        }

        return mData;
    }
}
