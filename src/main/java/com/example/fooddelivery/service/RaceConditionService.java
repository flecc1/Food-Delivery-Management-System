package com.example.fooddelivery.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class RaceConditionService {
    private int unsafeCounter = 0;
    private final AtomicInteger atomicCounter = new AtomicInteger(0);
    private int syncCounter = 0;

    public Map<String, Integer> demonstrateRaceCondition() throws InterruptedException {
        unsafeCounter = 0;
        atomicCounter.set(0);
        syncCounter = 0;

        int numberOfThreads = 50;
        int incrementsPerThread = 1000;
        int totalExpected = numberOfThreads * incrementsPerThread;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(totalExpected);

        log.debug("Starting Race Condition Demo. Expected total: {}", totalExpected);
        for (int i = 0; i < totalExpected; i++) {
            executor.submit(() -> {
                try {
                    unsafeCounter++;
                    atomicCounter.incrementAndGet();
                    incrementSync();
                } catch (Exception e) {
                    log.error("Error: {}", e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        log.info("Ожидаемое значение: {}, Unsafe: {}, Atomic: {}, Sync: {}",
                totalExpected, unsafeCounter, atomicCounter.get(), syncCounter);

        return Map.of(
                "1_expected", totalExpected,
                "2_unsafe_race_condition", unsafeCounter,
                "3_safe_atomic", atomicCounter.get(),
                "4_safe_synchronized", syncCounter
        );
    }

    private synchronized void incrementSync() {
        syncCounter++;
    }
}
