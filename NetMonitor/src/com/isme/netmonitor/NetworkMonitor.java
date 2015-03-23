package com.isme.netmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Handler;

/**
 * 网络监听
 * @author Administrator
 *
 */
public class NetworkMonitor {

	public static final int NET_GSM = 0x12;
	public static final int NET_WIFI = 0x13;
	public static final int NET_OFF = 0x14;

	private Context context;
	private Handler handler;
	private IntentFilter filter;
	private BroadcastReceiver netBroad;

	public NetworkMonitor(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;

		initializeBroad();
	}

	public void registerMonitor() {
		context.registerReceiver(netBroad, filter);
	}

	public void unregisterMonitor() {
		if(netBroad != null)
		{
			context.unregisterReceiver(netBroad);
		}
	}

	private void initializeBroad() {
		filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		netBroad = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				checkNet();
			}

		};
	}

	private void checkNet() {
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
			handler.sendEmptyMessage(NET_GSM);
			// Toast.makeText(context, "已连接 GSM 网络", Toast.LENGTH_SHORT).show();
			// 没有连接网络
		} else if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState != mobileState) {
			handler.sendEmptyMessage(NET_OFF);
			// Toast.makeText(context, "当前没有连接网络", Toast.LENGTH_SHORT).show();
			// WiFi网络
		} else if (wifiState != null && currentState == wifiState) {
			handler.sendEmptyMessage(NET_WIFI);
			// Toast.makeText(context, "已连接 Wifi 网络",Toast.LENGTH_SHORT).show();
		}
	}
	
}
