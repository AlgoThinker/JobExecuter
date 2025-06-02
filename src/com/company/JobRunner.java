package com.company;
public class JobRunner {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(4);

        Task taskA = new Task("A", "do something");
        Task taskB = new Task("B", "upload some file");
        Task taskC = new Task("C", "download some file");
        Task taskD = new Task("D", "do something else");

        scheduler.addTask(taskA);
        scheduler.addTask(taskB);
        scheduler.addTask(taskC);
        scheduler.addTask(taskD);

        scheduler.addDependency("C", "A"); // C depends on A
        scheduler.addDependency("D", "B"); // D depends on B
        scheduler.addDependency("D", "C"); // D depends on C

        scheduler.execute();
    }
}
