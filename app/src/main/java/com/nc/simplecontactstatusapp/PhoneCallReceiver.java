package com.nc.simplecontactstatusapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneCallReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    Context context;

    public PhoneCallReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals("android.intent.action.PHONE_STATE")) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phoneState != null) {
                if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    // Incoming call
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    if (getToggleStatus())
                        Toast.makeText(context, getDataFromSp() + " " + incomingNumber, Toast.LENGTH_LONG).show();
                } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    // Call answered or outgoing call
                    System.out.println("  is calling===============");

                } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    // Call ended
                    System.out.println("  is calling ended===============");
                }
            }
        }
    }

    String getDataFromSp() {
        sharedPreferences = MainActivity.cnt.getSharedPreferences(MainActivity.sPFileName, MODE_PRIVATE);
        return sharedPreferences.getString("status_meg", "Default status");
    }

    boolean getToggleStatus() {
        sharedPreferences = MainActivity.cnt.getSharedPreferences(MainActivity.sPFileName, MODE_PRIVATE);
        return sharedPreferences.getBoolean("status1", false);
    }

}
