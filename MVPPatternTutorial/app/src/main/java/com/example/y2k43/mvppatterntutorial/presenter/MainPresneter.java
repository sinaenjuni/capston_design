package com.example.y2k43.mvppatterntutorial.presenter;

import com.example.y2k43.mvppatterntutorial.IView;
import com.example.y2k43.mvppatterntutorial.MainFragment;
import com.example.y2k43.mvppatterntutorial.model.ITaskRepository;
import com.example.y2k43.mvppatterntutorial.model.TaskRepository;
import com.example.y2k43.mvppatterntutorial.model.domain.Task;

public class MainPresneter {
    private IView view;
    private ITaskRepository taskRepository;

    public void attachView(IView view) {
        this.view = view;
        this.taskRepository = TaskRepository.getInstance();
    }

    public void loadTask() {
        Task task = taskRepository.getTask(1);
        String title = task.getTitle() + "입니다";

        view.setTaskTitle(title);

    }
}
