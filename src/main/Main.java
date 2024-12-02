package main;

import scheduler.CpuScheduler;

public class Main {
    public static void main(String[] args) {
        // Initialize processes with arrival times, burst times, and optional priorities
        int[] arrivalTimes = {0, 2, 4, 6};
        int[] burstTimes = {7, 4, 1, 4};
        int[] priorities = {2, 1, 3, 2}; // Required only for Priority Scheduling
        int contextSwitchingDelay = 2;

        // Initialize the CPU Scheduling object
        CpuScheduler scheduler = new CpuScheduler(arrivalTimes, burstTimes, priorities, contextSwitchingDelay);

        // First, FCFS Scheduling
        System.out.println("=== FCFS Scheduling ===");
        scheduler.setStrategy("fcfs");
        scheduler.execute();
        scheduler.printProcesses();
        printAdditionalMetrics(scheduler);

        // Reset processes for the next scheduling algorithm
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
    }

    // Print additional metrics for each algorithm
    private static void printAdditionalMetrics(CpuScheduler scheduler) {
        System.out.printf("\nEfficiency: %.2f%%\n", scheduler.efficiency() * 100);
        System.out.printf("Throughput: %.2f processes/ms\n", scheduler.throughput());
        System.out.printf("Average Waiting Time: %.2f ms\n", scheduler.averageWaitingTime());
        System.out.printf("Average Turnaround Time: %.2f ms\n", scheduler.averageTurnaroundTime());
    }
}
