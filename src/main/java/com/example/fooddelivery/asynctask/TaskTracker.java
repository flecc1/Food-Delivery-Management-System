package com.example.fooddelivery.asynctask;

import com.example.fooddelivery.status.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TaskTracker {
    private final ConcurrentHashMap<UUID, TaskStatus> tasks = new ConcurrentHashMap<>();
    private final AtomicInteger totalTasksCreated = new AtomicInteger(0);

    public UUID createTask() {
        UUID taskId = UUID.randomUUID();
        tasks.put(taskId, TaskStatus.PENDING);
        totalTasksCreated.incrementAndGet();
        return taskId;
    }

    public TaskStatus getStatus(UUID taskId) {
        return tasks.get(taskId);
    }

    public void updateTask(UUID taskId, TaskStatus newStatus) {
        tasks.put(taskId, newStatus);
    }

    public int getTotalTasksCount() {
        return totalTasksCreated.get();
    }
}
