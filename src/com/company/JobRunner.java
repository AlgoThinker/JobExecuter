package com.company;

public class JobRunner {
    public static void main(String[] args) {
        JobExecutor jobExecutor = new JobExecutor(4);

        Step stepA = new Step("A", "do something");
        Step stepB = new Step("B", "upload some file");
        Step stepC = new Step("C", "download some file");
        Step stepD = new Step("D", "do something else");

        jobExecutor.addTask(stepA);
        jobExecutor.addTask(stepB);
        jobExecutor.addTask(stepC);
        jobExecutor.addTask(stepD);

        jobExecutor.addDependency("C", "A"); // C depends on A
        jobExecutor.addDependency("D", "B"); // D depends on B
        jobExecutor.addDependency("D", "C"); // D depends on C

        jobExecutor.execute();
    }
}