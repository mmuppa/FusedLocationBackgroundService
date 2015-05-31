package edu.uw.tacoma.mmuppa.fusedlocationbackgroundservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    private ToggleButton mToggleButton;
    private SharedPreferences mSharedPreferences;
    public static final String SHARED_PREFS = "sharedprefs";
    public static final String TRACKING = "tracking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        final Intent i = new Intent(this, GeoService.class);

        mToggleButton = (ToggleButton) findViewById(R.id.tracking_button);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean tracking) {
                if (tracking) {
                   startService(i);
                } else {
                    stopService(i);
                }
                editor.putBoolean(TRACKING, tracking);
                editor.commit();
            }
        });


    }

    @Override
    public  void onStart() {
        super.onStart();
        // Make sure to remember user's preference.
        if (mSharedPreferences.contains(MainActivity.TRACKING)) {
            boolean trackingOn = mSharedPreferences.getBoolean(MainActivity.TRACKING, false);
            mToggleButton.setChecked(trackingOn);
        }
    }
}
