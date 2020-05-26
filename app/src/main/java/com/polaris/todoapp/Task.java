package com.polaris.todoapp;

import androidx.annotation.NonNull;

public class Task {
    String id;
    String taskName;
    String userId;

    public Task(String id, String taskName, String userId) {
        this.id = id;
        this.taskName = taskName;
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return taskName.toString();
    }
}
