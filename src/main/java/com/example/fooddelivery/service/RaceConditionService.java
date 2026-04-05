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

    public Map<String, Integer> demonstrateRaceCondition() throws InterruptedException {
        int numberOfThreads = 50;
        int incrementsPerThread = 1000;
        int totalExpected = numberOfThreads * incrementsPerThread;

        final int[] unsafeCounter = {0};
        final AtomicInteger atomicCounter = new AtomicInteger(0);
        final int[] syncCounter = {0};
        final Object lock = new Object();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(totalExpected);

        log.debug("Starting Race Condition Demo. Expected total: {}", totalExpected);
        for (int i = 0; i < totalExpected; i++) {
            executor.submit(() -> {
                try {
                    unsafeCounter[0]++;
                    atomicCounter.incrementAndGet();
                    synchronized (lock) {
                        syncCounter[0]++;
                    }
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
                totalExpected, unsafeCounter[0], atomicCounter.get(), syncCounter[0]);

        return Map.of(
                "1_expected", totalExpected,
                "2_unsafe_race_condition", unsafeCounter[0],
                "3_safe_atomic", atomicCounter.get(),
                "4_safe_synchronized", syncCounter[0]
        );
    }
}
