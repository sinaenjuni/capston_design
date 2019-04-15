package com.example.y2k43.mvppatterntutorial.model;

import com.example.y2k43.mvppatterntutorial.model.domain.Task;

import java.util.HashMap;

public class TaskRepository implements ITaskRepository{

    private static TaskRepository instance;
    private HashMap<Integer, Task> tasks = new HashMap<>();

    private TaskRepository(){

    }

    public static TaskRepository getInstance(){
        if(instance == null){
            instance = new TaskRepository();
        }

        return instance;
    }

    public void insert(){

    }


    @Override
    public Task getTask(int id){
        Task task = new Task();
        task.setTitle("title!!");
        task.setContents("content");
        return task;

    }


}
