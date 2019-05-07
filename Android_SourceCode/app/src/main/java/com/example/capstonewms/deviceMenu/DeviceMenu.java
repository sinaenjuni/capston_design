package com.example.capstonewms.deviceMenu;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstonewms.R;
import com.example.capstonewms.mainDevice.MainDeviceMain;
import com.example.capstonewms.subDevice.SubDeviceMain;

public class DeviceMenu extends AppCompatActivity {

    private Button mainDeviceButton;
    private Button subDeviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_menu);

        mainDeviceButton = (Button) findViewById(R.id.deviceButtonMain);
        subDeviceButton = (Button)findViewById(R.id.deviceButtonSub);

        mainDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceMenu.this, MainDeviceMain.class));

            }
        });

        subDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceMenu.this, SubDeviceMain.class));
            }
        });

    }
}
