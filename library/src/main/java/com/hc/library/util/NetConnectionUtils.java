package com.hc.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetConnectionUtils {
	private static ConnectivityManager mConnectivityManager;

	private NetConnectionUtils(){}

	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;
	private final static String LOG_TAG = "NetConnectionUtils";


	/**
	 * 判断是否有网络
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.
				getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity == null) {
			Log.w(LOG_TAG, "couldn't get connectivity manager");
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].isAvailable()) {
						Log.d(LOG_TAG, "network is available");
						return true;
					}
				}
			}
		}
		Log.d(LOG_TAG, "network is not available");
		return false;
	}
	/**
	 * 检查网络状态
	 * @param context
	 * @return
	 */
	public synchronized static int getNetworkState(Context context){
		if(mConnectivityManager == null) {
			mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}

		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isAvailable()) {
			//网络连接
			if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){
				//WiFi网络
				if(netInfo.isConnectedOrConnecting()){
					return NETWORN_WIFI;
				}
			}else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){
				//有线网络
				if(netInfo.isConnectedOrConnecting()){
					return NETWORN_MOBILE;
				}
			}else if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){
				//3g网络
				if(netInfo.isConnectedOrConnecting()){
					return NETWORN_MOBILE;
				}
			}
		}

		return NETWORN_NONE;
	}


	/**
	 * 判断MOBILE网络是否可用
	 *
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isMobileDataEnable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.
				getSystemService(Context.CONNECTIVITY_SERVICE);

		return connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
	}


	/**
	 * 判断wifi 是否可用
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isWifiDataEnable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
	}

}
