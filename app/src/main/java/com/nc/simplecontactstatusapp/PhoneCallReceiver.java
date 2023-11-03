package com.nc.simplecontactstatusapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals("android.intent.action.PHONE_STATE")) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phoneState != null) {
                if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    // Incoming call
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    System.out.println(incomingNumber + "  is calling===============");
                    String message = incomingNumber + " Is try to call you.";
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    // Do something with the incoming call information
                } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    // Call answered or outgoing call
                    // You can do something here as well

                    System.out.println("  is calling===============");

                } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    // Call ended
                    // You can do something here too
                    System.out.println("  is calling ended===============");
                }
            }
        }
    }

}
