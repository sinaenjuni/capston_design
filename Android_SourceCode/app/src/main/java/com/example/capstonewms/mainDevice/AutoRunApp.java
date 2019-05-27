package com.example.capstonewms.mainDevice;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.capstonewms.model.SMSFunction;

public class AutoRunApp extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, SMSFunction.class);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, SMSFunction.class);
            context.startService(in);
        }
    }
}
