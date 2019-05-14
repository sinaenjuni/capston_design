package com.example.capstonewms.mainDevice;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.capstonewms.R;

public class MainDeviceMain extends AppCompatActivity {

    //InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device_main);

        //imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        getFragmentManager().beginTransaction().replace(R.id.main_framelayout, new WaitingFragment()).commit();
    }
}
