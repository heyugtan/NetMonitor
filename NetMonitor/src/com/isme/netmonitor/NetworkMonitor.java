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
 * �������
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

		// �ֻ���������
		if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState == mobileState) {
			handler.sendEmptyMessage(NET_GSM);
			// Toast.makeText(context, "������ GSM ����", Toast.LENGTH_SHORT).show();
			// û����������
		} else if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState != mobileState) {
			handler.sendEmptyMessage(NET_OFF);
			// Toast.makeText(context, "��ǰû����������", Toast.LENGTH_SHORT).show();
			// WiFi����
		} else if (wifiState != null && currentState == wifiState) {
			handler.sendEmptyMessage(NET_WIFI);
			// Toast.makeText(context, "������ Wifi ����",Toast.LENGTH_SHORT).show();
		}
	}
	
}
