package com.hc.library.base;

import android.app.Activity;
import android.app.Application;

import com.hc.library.util.ActivityList;
import com.hc.library.util.NetConnectionUtils;
import com.hc.library.util.OnNetStateChangedListener;

import java.util.ArrayList;
import java.util.List;


public class BaseApplication extends Application implements ActivityList, OnNetStateChangedListener {
    private static BaseApplication mApp;


    public static BaseApplication getInstance() {
        return mApp;
    }

    private ArrayList<Activity> mActivitys;
    /**
     * 当前所在位置
     * */

    public List<Activity> getActivitys() {
        return mActivitys;
    }
//
//    private int mNetworkState;
//
//
//    public int getNetState(){
//        return mNetworkState;
//    }
//
//    /**
//     * 更新网络状态
//     * */
//    public int updateNetworkState() {
//        int networkState = NetConnectionUtils.getNetworkState(getApplicationContext());
//        if (mNetworkState != networkState) {
//            mNetworkState = networkState;
//            onNetStateChanged(networkState);
//        }
//
//        return networkState;
//    }

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

//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        //初始化
//        EMClient.getInstance().init(getApplicationContext(), options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);
        //获取网络状态
//        mNetworkState = NetConnectionUtils.getNetworkState(getApplicationContext());
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
