package com.example.androidautostartup;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.widget.Toast;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoStartUp extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Service Start", Toast.LENGTH_LONG).show();
		// do something when the service is created



		BroadcastReceiver receiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
				if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
					// on AC power
					Toast.makeText(context, "Battery on Ac "+getDateTime1(), Toast.LENGTH_LONG).show();
				} else if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
					// on USB power
					Toast.makeText(context, "Battery usb "+getDateTime1(), Toast.LENGTH_LONG).show();
				} else if (plugged == 0) {
					// on battery power
					Toast.makeText(context, "Battery power "+getDateTime1(), Toast.LENGTH_LONG).show();
				} else {
					// intent didnt include extra info
				}
			}
		};
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(receiver, filter);











	}

	private String readBattery(){
		StringBuilder sb = new StringBuilder();
		IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryIntent = registerReceiver(null, batteryIntentFilter);

		int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		if(status == BatteryManager.BATTERY_STATUS_CHARGING){
			sb.append("BATTERY_STATUS_CHARGING\n");
		}
		if(status == BatteryManager.BATTERY_STATUS_FULL){
			sb.append("BATTERY_STATUS_FULL\n");
		}

		int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		if(plugged == BatteryManager.BATTERY_PLUGGED_USB){
			sb.append("BATTERY_PLUGGED_USB\n");
		}
		if(plugged == BatteryManager.BATTERY_PLUGGED_AC){
			sb.append("BATTERY_PLUGGED_AC\n");
		}

		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		sb.append("level: " + level + "\n");

		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		sb.append("scale: " + scale + "\n");

		return sb.toString();
	}


	private String getDateTime1() {
		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");  //SimpleDateFormat("yyyy MM dd HH mm ss SS");
		Date myDate = new Date();
		String filename = timeStampFormat.format(myDate);
		return filename;
	}
}