package com.example.capstonewms.mainDevice;

import android.Manifest;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonewms.R;
import com.example.capstonewms.model.SMSFunction;
import com.example.capstonewms.model.UserModel;
import com.example.capstonewms.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WaitingFragment extends Fragment {

    List<WaitingModel> waitingList;
    SMSFunction sms = new SMSFunction();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_waiting, container, false);

        //우측 상단 +버튼
        Button addButton = (Button)view.findViewById(R.id.main_fragment_button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                String index = waitingList.size()+1 + "";
                //FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                Log.e("index : ", index);

                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                        .child(index)
                        .setValue(new WaitingModel(index,"010" + "-" +
                                (int)(Math.random()*(9999 - 1001 +1)) + "-" +
                                (int)(Math.random()*(9999 - 1001 +1)) ,(int)(Math.random() * (10 - 1 +1)) + ""));
            }
        });

        //좌측 상단 SEND 버튼
        Button buttonSend = (Button) view.findViewById(R.id.main_fragment_button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String WaitNumber = waitingList.get(0).getNum();
                String people = waitingList.get(0).getPeople();
                String phoneNumber = waitingList.get(0).getPhoneNumber();

                phoneNumber = phoneNumber.replaceAll("-", "");

                String Text = "대기인 : " + WaitNumber + "\n" + "인원 수 : " + people + "\n" + "전화번호 : " + phoneNumber;

                //Toast.makeText(view.getContext(), "전화번호 : " + phoneNumber, Toast.LENGTH_SHORT).show();

                sms.setSms(Text);
                sms.setPhoneNo(phoneNumber);
                sms.sendSMS(getActivity(), view.getContext(), phoneNumber, Text);

                //Toast.makeText(view.getContext(), "WaitNumber : " + WaitNumber, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new WaitingFragmentRecyclerViewAdapter());

        return view;
    }


    private class WaitingFragmentRecyclerViewAdapter extends RecyclerView.Adapter {

        public WaitingFragmentRecyclerViewAdapter() {

            waitingList = new ArrayList<>();

            String uid = FirebaseAuth.getInstance().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").child("1").setValue(new WaitingModel("1","010-3672-8544","2"));
            //FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").child("2").setValue(new WaitingModel("2","010-0000-0000","4"));
            //FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").child("3").setValue(new WaitingModel("3","010-0000-0000","5"));
            //FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").child("4").setValue(new WaitingModel("4","010-0000-0000","2"));
            //FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").child("5").setValue(new WaitingModel("5","010-0000-0000","3"));

//            waitingList.add(new WaitingModel("1","010-0000-0000","4"));
//            waitingList.add(new WaitingModel("2","010-0000-0000","2"));
//            waitingList.add(new WaitingModel("3","010-0000-0000","1"));
//            waitingList.add(new WaitingModel("4","010-0000-0000","3"));

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //서버에서 넘어오는 데이터

                    waitingList.clear(); //누적 제거거
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        waitingList.add(snapshot.getValue(WaitingModel.class));
//                        waitingList.add(snapshot.getValue(WaitingModel.class));
                        Log.e("count : ", waitingList.size() + "");

                    }
                    notifyDataSetChanged();
                }

//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    //서버에서 넘어오는 데이터
//
//                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        WaitingModel waitingModel = snapshot.getValue(WaitingModel.class);
//
////                        usermodels.add(snapshot.getValue(UserModel.class));
////                        Log.e("user: ", new WaitingModel() = snapshot.getValue());
//
//                    }
//
//
//                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_waiting, viewGroup, false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

            final int num = i;

            ((CustomViewHolder)viewHolder).textNum.setText(waitingList.get(i).getNum());
            ((CustomViewHolder)viewHolder).textPeople.setText(waitingList.get(i).getPeople());
            ((CustomViewHolder)viewHolder).textPhone.setText(waitingList.get(i).getPhoneNumber());
            ((CustomViewHolder)viewHolder).buttonWaiting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), num + "번째 대기 버튼 눌림", Toast.LENGTH_SHORT).show();
                    Log.e(num + "번째 아이템 : ",
                            waitingList.get(num).getNum() + ", " +
                                    waitingList.get(num).getPeople() + ", " +
                                    waitingList.get(num).getPhoneNumber()
                            );
                }
            });
            ((CustomViewHolder)viewHolder).buttonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), num + "번째 호출 버튼 눌림", Toast.LENGTH_SHORT).show();
                    Log.e(num + "번째 아이템 : ",
                            waitingList.get(num).getNum() + ", " +
                                    waitingList.get(num).getPeople() + ", " +
                                    waitingList.get(num).getPhoneNumber()
                    );
                }
            });
            ((CustomViewHolder)viewHolder).buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String uid = FirebaseAuth.getInstance().getUid();
                    Toast.makeText(v.getContext(), num + "번째 삭제 버튼 눌림", Toast.LENGTH_SHORT).show();


                    for(int i = num; i < waitingList.size() - 1  ; i++) {   //0
                        Log.e(i + "번째 아이템 : ",
                                waitingList.get(i).getNum() + ", " +
                                        waitingList.get(i).getPeople() + ", " +
                                        waitingList.get(i).getPhoneNumber()
                        );

                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                               .child(i + 1 + "")
                               .setValue(new WaitingModel(i + 1 + "",waitingList.get(i+1).getPhoneNumber(),waitingList.get(i+1).getPeople()));

                    }

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                            .child(waitingList.size()+ "")
                            .removeValue();
                }
            });


        }

        @Override
        public int getItemCount() {
            return waitingList.size();
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView textNum;
        private TextView textPeople;
        private TextView textPhone;
        private Button buttonWaiting;
        private Button buttonCall;
        private Button buttonDelete;
        private Button buttonSend;

        public CustomViewHolder(final View view) {
            super(view);

            textNum = (TextView) view.findViewById(R.id.item_waiting_number);
            textPeople = (TextView) view.findViewById(R.id.item_waiting_people);
            textPhone = (TextView) view.findViewById(R.id.item_waiting_phone);

            buttonWaiting = (Button) view.findViewById(R.id.item_waiting_button_waiting);
            buttonCall = (Button) view.findViewById(R.id.item_waiting_button_call);
            buttonDelete = (Button) view.findViewById(R.id.item_waiting_button_delete);


        }
    }
}
