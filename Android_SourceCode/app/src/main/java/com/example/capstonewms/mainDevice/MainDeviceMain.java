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

import com.example.capstonewms.R;
import com.example.capstonewms.model.SMSFunction;

public class MainDeviceMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device_main);

        getFragmentManager().beginTransaction().replace(R.id.main_framelayout, new WaitingFragment()).commit();

//        //서비스 큐
//        Intent intent = new Intent(getApplicationContext(), SMSFunction.class);
//        startService(intent);
    }
}
