package com.company;

import java.util.*;
import java.util.concurrent.*;

public class JobExecutor {
    private final Map<String, Step> taskMap = new ConcurrentHashMap<>();
    private final ExecutorService executor;
    private final BlockingQueue<Step> readyQueue = new LinkedBlockingQueue<>();

    public JobExecutor(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void addTask(Step step) {
        taskMap.put(step.getId(), step);
    }

    public void addDependency(String taskId, String dependencyId) {
        Step step = taskMap.get(taskId);
        Step dependency = taskMap.get(dependencyId);
        if (step != null && dependency != null) {
            step.addDependency(dependency);
            dependency.addDependent(step);
        }
    }

    public void execute() {
        // Initialize ready queue with tasks having zero in-degree
        for (Step step : taskMap.values()) {
            if (step.getInDegree() == 0) {
                readyQueue.add(step);
            }
        }

        // Process tasks
        while (!readyQueue.isEmpty()) {
            Step step = readyQueue.poll();
            executor.submit(() -> {
                step.execute();
                for (Step dependent : step.getDependents()) {
                    int val = dependent.decrementInDegree();
                    System.out.println(dependent.getId() + "   " + val);
                    if (val == 0) {
                        readyQueue.add(dependent);
                    }
                }
            });
        }

        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
