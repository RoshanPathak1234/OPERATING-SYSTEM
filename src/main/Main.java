package main;

import cpu.Schedulers.*;

public class Main {
    public static void main(String[] args) {
        // Initialize processes with arrival times, burst times, and optional priorities
        int[] arrivalTimes = {0,1,2,3,4};
        int[] burstTimes = {4,3,1,2,5};
        int[] priorities = {1,1,1,1,1}; // Required only for Priority Scheduling
        int contextSwitchingDelay = 0;

        // Initialize the CPU Scheduling object
        scheduler scheduler = new scheduler(arrivalTimes, burstTimes, priorities, contextSwitchingDelay);

        // First, FCFS Scheduling
        System.out.println("=== FCFS Scheduling ===");
        scheduler.setStrategy("fcfs");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);
        scheduler.reset();


        // Second, SJF Scheduling
        System.out.println("\n=== SJF Scheduling ===");
        scheduler.setStrategy("sjf");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);

        // Reset processes for the next scheduling algorithm
        scheduler.reset();

        // Third, LJF Scheduling
        System.out.println("\n=== LJF Scheduling ===");
        scheduler.setStrategy("ljf");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);

        // Reset processes for the next scheduling algorithm
        scheduler.reset();

        // Fourth, HRRN Scheduling
        System.out.println("\n=== HRRN Scheduling ===");
        scheduler.setStrategy("hrrn");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);

        // Reset processes for the next scheduling algorithm
        scheduler.reset();

        // Fifth, Priority Scheduling
        System.out.println("\n=== Priority Scheduling ===");
        scheduler.setStrategy("priority");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);
        scheduler.reset();

        // sixth, Round Robin Scheduling
        System.out.println("\n=== Round Robin Scheduling ===");
        scheduler.setStrategy(new RoundRobin(1));
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);
        scheduler.reset();

        // seventh, SRTF Scheduling
        System.out.println("\n=== SRTF Scheduling ===");
        scheduler.setStrategy("srtf");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);
        scheduler.reset();


        // seventh, LRTF Scheduling
        System.out.println("\n=== LRTF Scheduling ===");
        scheduler.setStrategy("lrtf");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);
        scheduler.reset();

         System.out.println("=== MLFQ Scheduling ===");

        // Define the strategies for the queues
        Strategy[] strategies = {
            new FCFS(),       // Queue 1: FCFS
            new SJF(),        // Queue 2: SJF
            new RoundRobin(1), // Queue 3: Round Robin
            new SRTF()        // Queue 4: SRTF
        };

        // Initialize the MultiLevelFeedbackQueue with 4 levels and strategies
        MultiLevelFeedbackQueue mlfq = new MultiLevelFeedbackQueue(4, strategies);
        
        // Execute the MLFQ strategy
        scheduler.setStrategy(mlfq);
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);
        scheduler.reset();
    }

    // Print additional metrics for each algorithm
    private static void printAdditionalMetrics(scheduler scheduler) {
        System.out.printf("\nEfficiency: %.2f%%\n", scheduler.efficiency() * 100);
        System.out.printf("Cpu Utilization : %.2f %%\n" , scheduler.cpuUtilization());
        System.out.printf("Throughput: %.2f processes/ms\n", scheduler.throughput());
        System.out.printf("Average Waiting Time: %.2f ms\n", scheduler.averageWaitingTime());
        System.out.printf("Average Turnaround Time: %.2f ms\n", scheduler.averageTurnaroundTime());
    }
}
