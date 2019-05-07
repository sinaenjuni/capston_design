package com.example.capstonewms.mainDevice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.capstonewms.R;

public class MainDeviceMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device_main);

        getFragmentManager().beginTransaction().replace(R.id.main_framelayout, new WaitingFragment()).commit();
    }
}
