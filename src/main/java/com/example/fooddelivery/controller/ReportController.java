package com.example.fooddelivery.controller;

import com.example.fooddelivery.asyncTask.TaskTracker;
import com.example.fooddelivery.service.ReportService;
import com.example.fooddelivery.status.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    private final TaskTracker taskTracker;

    @PostMapping()
    public ResponseEntity<Map<String, UUID>> startReportGeneration() {
        UUID taskId = taskTracker.createTask();
        reportService.createReport(taskId);
        return ResponseEntity.accepted().body(Map.of("taskId", taskId));
    }

    @GetMapping("/status/{taskId}")
    public ResponseEntity<Map<String, String>> getTaskStatus(@PathVariable UUID taskId) {
        TaskStatus status = taskTracker.getStatus(taskId);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
                "taskId", taskId.toString(),
                "status", status.name()
        ));
    }

}
