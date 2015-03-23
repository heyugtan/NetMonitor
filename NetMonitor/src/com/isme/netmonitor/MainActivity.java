package com.isme.netmonitor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView tvNetStatus;
	private NetworkMonitor netMonitor;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NetworkMonitor.NET_GSM:
				tvNetStatus.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, "������ GSM ����", Toast.LENGTH_SHORT).show();
				break;
			case NetworkMonitor.NET_WIFI:
				tvNetStatus.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, "������ Wifi ����",Toast.LENGTH_SHORT).show();
				break;
			case NetworkMonitor.NET_OFF:
				if(tvNetStatus == null)
				{
					initializeView();
				}
				tvNetStatus.setText("���������ж�");
				tvNetStatus.setTextColor(Color.RED);
				tvNetStatus.setVisibility(View.VISIBLE);
				Toast.makeText(MainActivity.this, "�����ж�", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        netMonitor = new NetworkMonitor(this, handler);
        netMonitor.registerMonitor();
        
        initializeView();
    }

	private void initializeView() {
		tvNetStatus = (TextView) findViewById(R.id.tv_net_off);
	}
    
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		netMonitor.unregisterMonitor();
	}
    
}
