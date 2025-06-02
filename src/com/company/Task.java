package com.company;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
class Task {
    private final String id;
    private final String content;
    private final AtomicInteger inDegree = new AtomicInteger(0);
    private final List<Task> dependents = new ArrayList<>();

    public Task(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public int getInDegree() {
        return inDegree.get();
    }

    public List<Task> getDependents() {
        return dependents;
    }

    public void addDependency(Task dependency) {
        inDegree.incrementAndGet();
    }

    public void addDependent(Task dependent) {
        dependents.add(dependent);
    }

    public int decrementInDegree() {
        return inDegree.decrementAndGet();
    }

    public void execute() {
        System.out.println("[" + System.currentTimeMillis() + "] Starting Task " + id + " on Thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[" + System.currentTimeMillis() + "] Completed Task " + id + " on Thread " + Thread.currentThread().getName());
    }

}