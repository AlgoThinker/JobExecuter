package com.company;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
public class JobExecutor {
    private final Map<String, Step> steps = new HashMap<>();
    private final Map<String, CompletableFuture<Void>> futures = new ConcurrentHashMap<>();
    private final ExecutorService executor;

    public JobExecutor(Collection<Step> stepsCollection, ExecutorService executor) {
        for (Step step : stepsCollection) {
            steps.put(step.getId(), step);
        }
        this.executor = executor;
    }

    public void execute() {
        for (String stepId : steps.keySet()) {
            scheduleStep(stepId);
        }

        // Wait for all steps to complete
        CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0])).join();
    }

    private CompletableFuture<Void> scheduleStep(String stepId) {
        return futures.computeIfAbsent(stepId, id -> {
            Step step = steps.get(id);
            List<CompletableFuture<Void>> dependencyFutures = new ArrayList<>();

            for (String depId : step.getDependencies()) {
                dependencyFutures.add(scheduleStep(depId));
            }

            return CompletableFuture.allOf(dependencyFutures.toArray(new CompletableFuture[0]))
                    .thenRunAsync(() -> {
                        String threadName = Thread.currentThread().getName();
                        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
                        System.out.printf("[%s] Starting Step %s on Thread %s%n", time, step.getId(), threadName);
                        step.getTask().run();
                        String endTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
                        System.out.printf("[%s] Completed Step %s on Thread %s%n", endTime, step.getId(), threadName);
                    }, executor);
        });
    }
}