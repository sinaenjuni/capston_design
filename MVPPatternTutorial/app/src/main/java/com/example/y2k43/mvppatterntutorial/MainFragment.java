package com.example.y2k43.mvppatterntutorial;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.y2k43.mvppatterntutorial.model.TaskRepository;
import com.example.y2k43.mvppatterntutorial.model.domain.Task;
import com.example.y2k43.mvppatterntutorial.presenter.MainPresneter;

public class MainFragment extends Fragment implements IView, View.OnTouchListener {

    private MainPresneter mainPresneter;

    View rootview;
    TextView textView;
    RelativeLayout relativeLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootview = inflater.inflate(R.layout.activity_main_fragment, container, false);
        textView = (TextView)rootview.findViewById(R.id.text001);
        relativeLayout = (RelativeLayout)rootview.findViewById(R.id.back);


        mainPresneter = new MainPresneter();
        mainPresneter.attachView(this);
        mainPresneter.loadTask();

        rootview.setOnTouchListener(this);

        return rootview;
    }

    @Override
    public void setTaskTitle(String title) {
        textView.setText(title);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        relativeLayout.setBackgroundColor(Color.rgb(255,255,255));
        Toast.makeText(getContext(), "터치 감지", Toast.LENGTH_SHORT).show();

        return false;
    }
}
