package com.example.fooddelivery.asyncTask;

import com.example.fooddelivery.status.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskTracker {
    private final ConcurrentHashMap<UUID, TaskStatus> tasks = new ConcurrentHashMap<>();

    public UUID createTask() {
        UUID taskId = UUID.randomUUID();
        tasks.put(taskId, TaskStatus.PENDING);
        return taskId;
    }

    public TaskStatus getStatus(UUID taskId) {
        return tasks.get(taskId);
    }

    public void updateTask(UUID taskId, TaskStatus newStatus) {
        tasks.put(taskId, newStatus);
    }
}
