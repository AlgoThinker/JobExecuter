package com.company;

import java.util.*;
import java.util.concurrent.*;
class Scheduler {
    private final ExecutorService executor;

    public Scheduler(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void execute(Task rootTask) {
        try {
            dfs(rootTask);
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void dfs(Task task) {
        List<Future<?>> futures = new ArrayList<>();

        // Execute prerequisites first
        for (Task prerequisite : task.getPrerequisites()) {
            futures.add(executor.submit(() -> dfs(prerequisite)));
        }

        // Wait for all prerequisites to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Task execution interrupted", e);
            }
        }

        // Execute current task
        task.execute();
    }
}
