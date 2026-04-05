package com.example.fooddelivery.controller;

import com.example.fooddelivery.service.RaceConditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
@Tag(name = "Демонстрация", description = "Демонстрация многопоточных проблем")
public class RaceConditionController {

    private final RaceConditionService raceConditionService;

    @GetMapping("/race-condition")
    @Operation(summary = "Запуск теста на состояние гонки (Race Condition)")
    public ResponseEntity<Map<String, Integer>> runRaceConditionDemo() throws InterruptedException {
        Map<String, Integer> result = raceConditionService.demonstrateRaceCondition();
        return ResponseEntity.ok(result);
    }
}