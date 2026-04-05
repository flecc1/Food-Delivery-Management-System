package com.example.fooddelivery.service;

import com.example.fooddelivery.asyncTask.TaskTracker;
import com.example.fooddelivery.status.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskTracker taskTracker;

    @Async("taskExecutor")
    public CompletableFuture<Void> createReport(UUID taskId) {
        taskTracker.updateTask(taskId, TaskStatus.IN_PROGRESS);

        try {
            Thread.sleep(5000);
            taskTracker.updateTask(taskId, TaskStatus.COMPLETED);
        } catch (InterruptedException e) {
            taskTracker.updateTask(taskId, TaskStatus.FAILED);
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(null);
    }
}
