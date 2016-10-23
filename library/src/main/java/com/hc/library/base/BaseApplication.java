package com.hc.library.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;

import com.hc.library.pojo.Carousel;
import com.hc.library.pojo.User;
import com.hc.library.util.ActivityList;
import com.hc.library.util.NetConnectionUtils;
import com.hc.library.util.OnNetStateChangedListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class BaseApplication extends Application implements ActivityList, OnNetStateChangedListener {

    private static BaseApplication mApp;

    public static BaseApplication getInstance() {
        return mApp;
    }

    private ArrayList<Activity> mActivitys;

    public List<Activity> getActivitys() {
        return mActivitys;
    }

    private static User user;

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    private static List<Carousel> cl;

    public void setCarouselList(List<Carousel> cl)
    {
        this.cl = cl;
    }

    public List<Carousel> getCarouselList()
    {
        return cl;
    }

    @Override
    public void onNetStateChanged(int networkState) {
        for(Activity activity : mActivitys) {
            if (activity instanceof OnNetStateChangedListener) {
                ((OnNetStateChangedListener) activity).onNetStateChanged(networkState);
            }
        }
    }

    @Override
    public void onCreate() {
        mApp = this;
        super.onCreate();
        mActivitys = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }


    @Override
    public void addActivity(Activity activity) {
        mActivitys.add(activity);
    }

    @Override
    public Activity getCurrentActivity() {
        int size = activitySize();
        if (size > 0) {
            return mActivitys.get(activitySize() - 1);
        } else {
            return null;
        }
    }

    @Override
    public boolean removeActivity(Activity activity) {
        return mActivitys.remove(activity);
    }

    @Override
    public Activity removeActivity(int position) {
        return mActivitys.remove(position);
    }

    @Override
    public boolean removeActivity(Class<? extends Activity> clazz) {
        boolean b = false;

        int size = activitySize();
        for (int i = 0; i <= size; i++) {
            if (mActivitys.get(i).getClass() == clazz) {
                mActivitys.remove(mActivitys.get(i));
                b = true;
            }
        }

        return b;
    }

    @Override
    public void finishActivity(Class<? extends Activity> clazz) {

        int size = activitySize();
        for (int i = 0; i <= size; i++) {
            if (mActivitys.get(i).getClass() == clazz) {
                mActivitys.remove(mActivitys.get(i));
                mActivitys.get(i).finish();
            }
        }
    }

    @Override
    public Activity getActivity(int position) {
        return mActivitys.get(position);
    }

    @Override
    public <A extends Activity> A getActivity(int position, Class<A> clazz) {
        Activity activity = getActivity(position);
        return activity == null ? null : clazz.cast(activity);
    }

    @Override
    public void clearActivity() {
        mActivitys.clear();
    }

    @Override
    public void finishActivity(Activity activity) {
        if (removeActivity(activity)) {
            activity.finish();
        }
    }

    @Override
    public void finishActivity(int position) {
        Activity activity = mActivitys.remove(position);
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void finishAllActivity() {
        int size = activitySize();
        for (int i = 0; i <= size; i++) {
            finishActivity(mActivitys.get(i));
        }
    }

    @Override
    public boolean containsActivity(Activity activity) {
        return mActivitys.contains(activity);
    }

    @Override
    public int activitySize() {
        return mActivitys.size();
    }
}
