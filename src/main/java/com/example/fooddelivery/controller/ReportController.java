package com.example.fooddelivery.controller;

import com.example.fooddelivery.asyncTask.TaskTracker;
import com.example.fooddelivery.service.ReportService;
import com.example.fooddelivery.status.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@Tag(name = "Асинхронные отчеты", description = "Управление фоновыми задачами генерации отчетов")
public class ReportController {
    private final ReportService reportService;
    private final TaskTracker taskTracker;

    @PostMapping()
    @Operation(
            summary = "Запуск генерации отчета",
            description = "Создает фоновую задачу на формирование отчета." +
                    " Возвращает UUID задачи для отслеживания статуса."
    )
    public ResponseEntity<Map<String, UUID>> startReportGeneration() {
        log.info("request to start generation of reports");
        UUID taskId = taskTracker.createTask();
        reportService.createReport(taskId);
        return ResponseEntity.accepted().body(Map.of("taskId", taskId));
    }

    @GetMapping("/status/{taskId}")
    @Operation(
            summary = "Проверка статуса задачи",
            description = "Позволяет узнать текущее состояние задачи" +
                    " по её идентификатору (PENDING, IN_PROGRESS, COMPLETED, FAILED)."
    )
    public ResponseEntity<Map<String, String>> getTaskStatus(@PathVariable UUID taskId) {
        log.info("request to get status of task {}", taskId);
        TaskStatus status = taskTracker.getStatus(taskId);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
                "taskId", taskId.toString(),
                "status", status.name()
        ));
    }

    @GetMapping("/stats")
    @Operation(summary = "Получение статистики счетчиков")
    public ResponseEntity<Map<String, Integer>> getStats() {
        return ResponseEntity.ok(Map.of(
                "totalTasks", taskTracker.getTotalTasksCount()
        ));
    }

}
