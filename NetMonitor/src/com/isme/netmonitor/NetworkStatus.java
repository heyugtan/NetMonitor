package com.isme.netmonitor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

public class NetworkStatus {

	/**
	 * 判断当前网络连接状态
	 * 
	 * @param context
	 */
	public static void checkNetworkState(Context context) {
		State wifiState = null;
		State mobileState = null;
		NetworkInfo temp = null;
		State currentState = State.CONNECTED;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiState = (temp = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)) == null ? null
				: temp.getState();
		mobileState = (temp = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)) == null ? null
				: temp.getState();
		
		// 手机网络连接
		if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState == mobileState) {
			Toast.makeText(context, "已连接 GSM 网络", Toast.LENGTH_SHORT).show();
			// 没有连接网络
		} else if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState != mobileState) {
			Toast.makeText(context, "当前没有连接网络", Toast.LENGTH_SHORT).show();
			// WiFi网络
		} else if (wifiState != null && currentState == wifiState) {
			Toast.makeText(context, "已连接 Wifi 网络", Toast.LENGTH_SHORT).show();
		}
	}

}
