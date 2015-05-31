package edu.uw.tacoma.mmuppa.fusedlocationbackgroundservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class GeoReceiver extends BroadcastReceiver {

  private SharedPreferences mPrefs;
	public static final String TAG = "GeoReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		// Rebooted
		if( "android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            // Check to see if user turned tracking on or off.
            boolean trackingOn = false;
	        mPrefs = context.getSharedPreferences(MainActivity.SHARED_PREFS,
	                Context.MODE_PRIVATE);
	        if (mPrefs.contains(MainActivity.TRACKING)) {
	            trackingOn = mPrefs.getBoolean(MainActivity.TRACKING, false);
	        }
	        if(trackingOn){
				ComponentName comp = new ComponentName(context.getPackageName(), GeoService.class.getName());
				ComponentName service = context.startService(new Intent().setComponent(comp));
				
				if (null == service){
					Log.e(TAG, "Could not start service " + comp.toString());
				}
	        }
			
		} else {
			Log.e(TAG, "Received unexpected intent " + intent.toString());
		}
	}
}