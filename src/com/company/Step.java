package com.company;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Step {
    private final String id;
    private final String content;
    private final List<Step> dependencies = new ArrayList<>();
    private final List<Step> dependents = new ArrayList<>();
    private final AtomicInteger inDegree = new AtomicInteger(0);

    public Step(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void addDependency(Step step) {
        dependencies.add(step);
        inDegree.incrementAndGet();
    }

    public void addDependent(Step step) {
        dependents.add(step);
    }

    public int decrementInDegree() {
        return inDegree.decrementAndGet();
    }

    public int getInDegree() {
        return inDegree.get();
    }

    public List<Step> getDependents() {
        return dependents;
    }

    public void execute() {
        String threadName = Thread.currentThread().getName();
        String startTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        System.out.printf("[%s] Starting Task %s on Thread %s%n", startTime, id, threadName);
        // Simulate task execution
        try {
            Thread.sleep(2000); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String endTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        System.out.printf("[%s] Completed Task %s on Thread %s%n", endTime, id, threadName);
    }
}