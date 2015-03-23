package com.isme.netmonitor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

public class NetworkStatus {

	/**
	 * �жϵ�ǰ��������״̬
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
		
		// �ֻ���������
		if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState == mobileState) {
			Toast.makeText(context, "������ GSM ����", Toast.LENGTH_SHORT).show();
			// û����������
		} else if (wifiState != null && mobileState != null
				&& currentState != wifiState && currentState != mobileState) {
			Toast.makeText(context, "��ǰû����������", Toast.LENGTH_SHORT).show();
			// WiFi����
		} else if (wifiState != null && currentState == wifiState) {
			Toast.makeText(context, "������ Wifi ����", Toast.LENGTH_SHORT).show();
		}
	}

}
