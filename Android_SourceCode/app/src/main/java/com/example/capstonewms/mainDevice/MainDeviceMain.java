package com.example.capstonewms.mainDevice;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.capstonewms.R;
import com.example.capstonewms.model.SMSFunction;

public class MainDeviceMain extends AppCompatActivity {
    private Intent serviceIntent;
    SMSFunction smsFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device_main);

        getFragmentManager().beginTransaction().replace(R.id.main_framelayout, new WaitingFragment()).commit();

        //앱 실행으로 서비스를 킴, 메인 디바이스 전용 모드 진입시 호출
        if(SMSFunction.serviceIntent == null) {
            serviceIntent = new Intent(getApplicationContext(), SMSFunction.class);
            startService(serviceIntent);
        } else {
            serviceIntent = SMSFunction.serviceIntent; //getInstance().getApplication();
            Toast.makeText(getApplicationContext(), "Already Sevice Running", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceIntent != null) {
            //stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}
