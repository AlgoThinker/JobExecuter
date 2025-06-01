package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobRunner {
    public static void main(String[] args) {
        // Define the steps with simulated work using sleep
        Step stepA = new Step("A", () -> {
            sleep(1000);
            System.out.println("Executing A");
        }, Collections.emptyList());

        Step stepB = new Step("B", () -> {
            sleep(1000);
            System.out.println("Executing B");
        }, Collections.emptyList());

        Step stepC = new Step("C", () -> {
            sleep(1000);
            System.out.println("Executing C");
        }, Arrays.asList("A"));

        Step stepD = new Step("D", () -> {
            sleep(1000);
            System.out.println("Executing D");
        }, Arrays.asList("B", "C"));

        // Create a thread pool with a fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Initialize the job executor with the steps and executor service
        JobExecutor jobExecutor = new JobExecutor(Arrays.asList(stepA, stepB, stepC, stepD), executorService);

        // Execute the job
        jobExecutor.execute();

        // Shutdown the executor service
        executorService.shutdown();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
