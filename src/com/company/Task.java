package com.company;

import java.util.*;

class Task {
    private final String id;
    private final String content;
    private final List<Task> prerequisites = new ArrayList<>();

    public Task(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public List<Task> getPrerequisites() {
        return prerequisites;
    }

    public void addPrerequisite(Task task) {
        prerequisites.add(task);
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
