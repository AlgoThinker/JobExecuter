package com.company;

import java.util.*;
import java.util.concurrent.*;

public class Step {
    private final String id;
    private final Runnable task;
    private final List<String> dependencies;

    public Step(String id, Runnable task, List<String> dependencies) {
        this.id = id;
        this.task = task;
        this.dependencies = dependencies;
    }

    public String getId() {
        return id;
    }

    public Runnable getTask() {
        return task;
    }

    public List<String> getDependencies() {
        return dependencies;
    }
}
