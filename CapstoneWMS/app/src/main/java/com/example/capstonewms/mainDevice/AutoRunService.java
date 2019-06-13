package com.example.capstonewms.mainDevice;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.capstonewms.model.SMSFunction;

public class AutoRunService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //안드로이드 버전 확인하는 부분
        //오레오 이상이면 startForegroundService를 통해서 뒤지지 않는 서비스 생성
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             Intent in = new Intent(context, SMSFunctionRestart.class);
             context.startForegroundService(in);
         } else {
             Intent in = new Intent(context, SMSFunction.class);
             context.startService(in);
         }
    }
}
