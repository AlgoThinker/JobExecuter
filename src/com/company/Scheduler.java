package com.company;

import java.util.*;
import java.util.concurrent.*;
class Scheduler {
    private final Map<String, Task> taskMap = new ConcurrentHashMap<>();
    private final ExecutorService executor;
    private final BlockingQueue<Task> readyQueue = new LinkedBlockingQueue<>();

    public Scheduler(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void addDependency(String taskId, String dependencyId) {
        Task task = taskMap.get(taskId);
        Task dependency = taskMap.get(dependencyId);
        if (task != null && dependency != null) {
            task.addDependency(dependency);
            dependency.addDependent(task);
            System.out.println("Added dependency: " + task.getId() + " depends on " + dependency.getId());
        }
    }

    public void execute() {
        // Initialize ready queue with tasks having zero in-degree
        for (Task task : taskMap.values()) {
            System.out.println("Task " + task.getId() + " has inDegree: " + task.getInDegree());
            if (task.getInDegree() == 0) {
                readyQueue.add(task);
                System.out.println("Task " + task.getId() + " added to readyQueue.");
            }
        }

        // Process tasks
        while (!readyQueue.isEmpty()) {
            Task task = readyQueue.poll();
            executor.submit(() -> {
                task.execute();
                for (Task dependent : task.getDependents()) {
                    int newInDegree = dependent.decrementInDegree();
                    System.out.println("Task " + dependent.getId() + " new inDegree: " + newInDegree);
                    if (newInDegree == 0) {
                        readyQueue.add(dependent);
                        System.out.println("Task " + dependent.getId() + " added to readyQueue.");
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
