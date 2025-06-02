package com.company;
public class JobRunner {
    public static void main(String[] args) {

        /*

        A   F   E
        |   |   |
        |   |   |
        C   B   |
        |  /   /
        | /  /
         D

         */

        Task taskA = new Task("A", "do something");
        Task taskB = new Task("B", "upload some file");
        Task taskC = new Task("C", "download some file");
        Task taskD = new Task("D", "do something else");
        Task taskE = new Task("E", "do something else");
        Task taskF = new Task("F", "do something else");

        taskC.addPrerequisite(taskA); // C depends on A
        taskD.addPrerequisite(taskB); // D depends on B
        taskD.addPrerequisite(taskC); // D depends on C
        taskD.addPrerequisite(taskE); // D depends on E
        taskB.addPrerequisite(taskF); // B depends on F

        Scheduler executor = new Scheduler(6);
        executor.execute(taskD); // Start from the root (D)
    }
}


