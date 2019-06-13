package com.example.capstonewms.subDevice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonewms.R;
import com.example.capstonewms.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class SubDeviceMain extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewEnter;
    private TextView textViewPeople;
    private TextView textViewPhone;
    private TextView textViewClear;
    private List<WaitingModel> waitingList;


    private String[] textViewNumberString = {"0", "1", "2","3","4","5","6","7","8","9"};
    private TextView[] textViewNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_device_main);

        textViewPhone = (TextView)findViewById(R.id.subDeviceMain_TextView_phone);
        textViewPhone.setText("010-"); // 초기화

        textViewClear = (TextView)findViewById(R.id.subDeviceMain_TextView_clear);

        textViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempStr = textViewPhone.getText().toString();
                int length = tempStr.length();
                if(!tempStr.equals("")) {
                    if(tempStr.length() == 4 || tempStr.length() == 9) {
                        tempStr = tempStr.substring(0, length - 2);
                    } else {
                        tempStr = tempStr.substring(0, length - 1);
                    }
                    textViewPhone.setText(tempStr);
                } else {
                    Toast.makeText(SubDeviceMain.this, "그만눌러", Toast.LENGTH_LONG).show();
                }
                //textViewPhone.setText(textViewPhone.getText().toString().substring(0, textViewPeople.getText().length()));
            }
        });

        textViewNumber = new TextView[textViewNumberString.length];
        for(int i = 0; i < textViewNumberString.length; i++){
            textViewNumber[i] = (TextView) findViewById(R.id.subDeviceMain_TextView_number0 + i);
            textViewNumber[i].setOnClickListener(this);
        }


        waitingList = new ArrayList<>();
        textViewPeople = (TextView)findViewById(R.id.subDeviceMain_TextView_People);
        textViewEnter = (TextView) findViewById(R.id.subDeviceMain_TextView_enter);
        textViewEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempStr = textViewPhone.getText().toString();
                if(tempStr.length() != 13) {
                    Toast.makeText(SubDeviceMain.this, "전화번호를 바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    textViewPhone.setText("010-"); // 재초기화
                    Intent intent = new Intent(SubDeviceMain.this, SubDevicePeople.class);
                    intent.putExtra("phone", tempStr);
                    startActivity(intent);

                }
            }
        });


        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //서버에서 넘어오는 데이터
                        waitingList.clear(); //누적 제거
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            waitingList.add(snapshot.getValue(WaitingModel.class));
                            Log.e("count : ", waitingList.size() + "");
                        }
                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewPeople.setText(waitingList.size()+"");
                        }
                    });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int selButton = v.getId();
        int textPhonelength = textViewPhone.getText().length();

        switch (selButton){
            case R.id.subDeviceMain_TextView_number0:
                textViewPhone.setText(textViewPhone.getText().toString() + "0");
                break;
            case R.id.subDeviceMain_TextView_number1:
                textViewPhone.setText(textViewPhone.getText().toString() + "1");
                break;
            case R.id.subDeviceMain_TextView_number2:
                textViewPhone.setText(textViewPhone.getText().toString() + "2");
                break;
            case R.id.subDeviceMain_TextView_number3:
                textViewPhone.setText(textViewPhone.getText().toString() + "3");
                break;
            case R.id.subDeviceMain_TextView_number4:
                textViewPhone.setText(textViewPhone.getText().toString() + "4");
                break;
            case R.id.subDeviceMain_TextView_number5:
                textViewPhone.setText(textViewPhone.getText().toString() + "5");
                break;
            case R.id.subDeviceMain_TextView_number6:
                textViewPhone.setText(textViewPhone.getText().toString() + "6");
                break;
            case R.id.subDeviceMain_TextView_number7:
                textViewPhone.setText(textViewPhone.getText().toString() + "7");
                break;
            case R.id.subDeviceMain_TextView_number8:
                textViewPhone.setText(textViewPhone.getText().toString() + "8");
                break;
            case R.id.subDeviceMain_TextView_number9:
                textViewPhone.setText(textViewPhone.getText().toString() + "9");
                break;
        }

        if(textPhonelength == 2 || textPhonelength == 7) {
            String tempStr = textViewPhone.getText().toString();
            tempStr = tempStr + "-";
            textViewPhone.setText(tempStr);
        }

        if(textPhonelength > 12) {
            Toast.makeText(SubDeviceMain.this, "올바른 전화번호가 아닙니다", Toast.LENGTH_SHORT).show();
            String tempStr = textViewPhone.getText().toString();
            int length = tempStr.length();
            tempStr = tempStr.substring(0, length-1);
            textViewPhone.setText(tempStr);
        }
    }
}

