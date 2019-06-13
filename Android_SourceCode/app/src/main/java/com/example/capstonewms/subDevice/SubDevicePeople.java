package com.example.capstonewms.subDevice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonewms.R;
import com.example.capstonewms.model.MessageModel;
import com.example.capstonewms.model.SMSFunction;
import com.example.capstonewms.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubDevicePeople extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewEnter;
    private TextView textViewPeople;
    private TextView textViewClear;
    private TextView textViewWaitPeople;
    private List<WaitingModel> waitingList;
    private List<MessageModel> messageList;


    private String[] textViewNumberString = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    SMSFunction smsFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_device_people);

        smsFunction = new SMSFunction(getApplicationContext(), SubDevicePeople.this);

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        Toast.makeText(SubDevicePeople.this, "전화번호 : " + phone, Toast.LENGTH_LONG).show();

        textViewClear = (TextView) findViewById(R.id.subDeviceMain_TextView_clear);
        textViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempStr = textViewWaitPeople.getText().toString();
                int length = tempStr.length();
                if (!tempStr.equals("")) {
                    if(textViewWaitPeople.getText().length() == 1) {
                        tempStr = "0";
                    } else {
                        tempStr = tempStr.substring(0, length - 1);
                    }
                    textViewWaitPeople.setText(tempStr);
                } else {
                    textViewWaitPeople.setText("0");
                    Toast.makeText(SubDevicePeople.this, "더 이상 입력이 불가능합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView[] textViewNumber = new TextView[textViewNumberString.length];
        for (int i = 0; i < textViewNumberString.length; i++) {
            textViewNumber[i] = (TextView) findViewById(R.id.subDeviceMain_TextView_number0 + i);
            textViewNumber[i].setOnClickListener(this);
        }

        waitingList = new ArrayList<>();

        textViewWaitPeople = (TextView)findViewById(R.id.subDeviceMain_TextView_waitPeople);
        textViewPeople = (TextView)findViewById(R.id.subDeviceMain_TextView_People);
        textViewEnter = (TextView) findViewById(R.id.subDeviceMain_TextView_enter);
        textViewEnter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //서버에서 넘어오는 데이터
                                waitingList.clear(); //누적 제거거
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    waitingList.add(snapshot.getValue(WaitingModel.class));
                                    Log.e("count : ", waitingList.size() + "");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                if(textViewWaitPeople.getText().equals("0")) {
                    Toast.makeText(SubDevicePeople.this, "인원이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubDevicePeople.this, "추가함", Toast.LENGTH_LONG).show();

                    Intent intent = getIntent();
                    String phone = intent.getStringExtra("phone");
                    String waitingsize = (waitingList.size() + 1 ) + "";

                    String tempStr = "정상적으로 대기가 등록되었습니다. \n" +
                            "현재 대기 순서 : " + waitingsize + "\n" +
                            "인원 수 : " + textViewWaitPeople.getText() + "\n" +
                            "전화번호 : " + phone;

                    //startActivity(new Intent(SubDevicePeople.this, SubDeviceMain.class));

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                            .child(waitingList.size()+1 + "")
                            .setValue(new WaitingModel((waitingList.size()+1) + "", phone, textViewWaitPeople.getText().toString(), tempStr));

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("smslist")
                            .push()
                            .setValue(new MessageModel(phone, tempStr));

                    finish();
                }
            }
        });

        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //서버에서 넘어오는 데이터
                        waitingList.clear(); //누적 제거거
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

    public void checkRangeOverflow(String number, int text) {
         String tempStr = Integer.toString(text);
         textViewWaitPeople.setText(String.format("%s%s", tempStr, number));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int selButton = v.getId();
        int text = Integer.parseInt(textViewWaitPeople.getText().toString());

        if(text > 100) {
            Toast.makeText(SubDevicePeople.this, "100명은 넘길수 없다.", Toast.LENGTH_LONG).show();
            textViewWaitPeople.setText("100");
            return;
        }

        switch (selButton) {
            case R.id.subDeviceMain_TextView_number0:
                checkRangeOverflow("0", text);
                break;
            case R.id.subDeviceMain_TextView_number1:
                checkRangeOverflow("1", text);
                break;
            case R.id.subDeviceMain_TextView_number2:
                checkRangeOverflow("2", text);
                break;
            case R.id.subDeviceMain_TextView_number3:
                checkRangeOverflow("3", text);
                break;
            case R.id.subDeviceMain_TextView_number4:
                checkRangeOverflow("4", text);
                break;
            case R.id.subDeviceMain_TextView_number5:
                checkRangeOverflow("5", text);
                break;
            case R.id.subDeviceMain_TextView_number6:
                checkRangeOverflow("6", text);
                break;
            case R.id.subDeviceMain_TextView_number7:
                checkRangeOverflow("7", text);
                break;
            case R.id.subDeviceMain_TextView_number8:
                checkRangeOverflow("8", text);
                break;
            case R.id.subDeviceMain_TextView_number9:
                checkRangeOverflow("9", text);
                break;
        }

        text = Integer.parseInt(textViewWaitPeople.getText().toString());
        textViewWaitPeople.setText(Integer.toString(text));

        if(text > 100) {
            Toast.makeText(SubDevicePeople.this, "100명은 넘길수 없다.", Toast.LENGTH_LONG).show();
            textViewWaitPeople.setText("100");
        }
    }
}
