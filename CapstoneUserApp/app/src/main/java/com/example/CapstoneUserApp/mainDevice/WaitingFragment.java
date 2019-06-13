package com.example.CapstoneUserApp.mainDevice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CapstoneUserApp.model.SMSFunction;
import com.example.CapstoneUserApp.model.WaitingModel;
import com.example.capstonewms.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WaitingFragment extends Fragment {

    List<WaitingModel> waitingList;
    SMSFunction smsFunction;
    View thisFragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_waiting, container, false);
        thisFragmentView = view; //View가 전역변수면 별로 좋은것은 아니니 나중에 해결할것
        smsFunction = new SMSFunction(view.getContext() ,getActivity());

        return view;
    }

    private class WaitingFragmentRecyclerViewAdapter extends RecyclerView.Adapter {

        public WaitingFragmentRecyclerViewAdapter() {

            waitingList = new ArrayList<>();

            String uid = FirebaseAuth.getInstance().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").child("1").setValue(new WaitingModel("1","010-9268-1339","2"));

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

            final String WaitNumber = waitingList.get(i).getNum();
            final String people = waitingList.get(i).getPeople();
            final String phoneNumber = waitingList.get(i).getPhoneNumber();

            ((CustomViewHolder)viewHolder).buttonWaiting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), num + 1 + "번째 대기 상태 알림을 전송합니다.", Toast.LENGTH_SHORT).show();

                    Log.e(num + "번째 아이템 : ",
                            waitingList.get(num).getNum() + ", " +
                                    waitingList.get(num).getPeople() + ", " +
                                    waitingList.get(num).getPhoneNumber()
                            );

                    String tempStr = "안녕하세요, 대기 안내입니다. \n" +
                            "대기 순서 : " + WaitNumber + "\n" +
                            "남은 대기 인원 : " + waitingList.size() + 1  + "\n" +
                            "인원 수 : " + people + "\n" +
                            "전화번호 : " + phoneNumber;

                    smsFunction.sendSMS(waitingList.get(num).getPhoneNumber(), tempStr);
            }
            });
            ((CustomViewHolder)viewHolder).buttonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), num + 1 + "번째 대기를 호출합니다.", Toast.LENGTH_SHORT).show();

                    Log.e(num + "번째 아이템 : ",
                            waitingList.get(num).getNum() + ", " +
                                    waitingList.get(num).getPeople() + ", " +
                                    waitingList.get(num).getPhoneNumber()
                    );

                    String tempStr = "안녕하세요, 호출 안내입니다.\n 바로 와주시기바랍니다.\n" +
                            "대기 순서 : " + WaitNumber + "\n" +
                            "인원 수 : " + people + "\n" +
                            "전화번호 : " + phoneNumber;
                    smsFunction.sendSMS(waitingList.get(num).getPhoneNumber(), tempStr);
                }
            });
            ((CustomViewHolder)viewHolder).buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String uid = FirebaseAuth.getInstance().getUid();
                    Toast.makeText(v.getContext(), num + 1+ "번째 대기를 삭제하였습니다.", Toast.LENGTH_SHORT).show();


                    for(int i = num; i < waitingList.size() - 1; i++) {   //0
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
