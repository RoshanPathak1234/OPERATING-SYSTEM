package scheduler;

import java.util.List;
import java.util.Locale;

public class CpuScheduler {

    private Strategy strategy;
    private Process[] processes;
    private int contextSwitchingDelay;

    // Constructor with all parameters optional
    public CpuScheduler(Object arrivalTimes, Object burstTimes, Object priorities) {
        this.contextSwitchingDelay = 0; // Default to 0
        this.processes = initializeProcesses(arrivalTimes, burstTimes, priorities);
    }
    public CpuScheduler(Object arrivalTimes, Object burstTimes, Object priorities , int contextSwitchingDelay) {
        this.contextSwitchingDelay = contextSwitchingDelay;
        this.processes = initializeProcesses(arrivalTimes, burstTimes, priorities);
    }

    // Initialize processes
    private Process[] initializeProcesses(Object arrivalTimes, Object burstTimes, Object priorities) {
        int[] arrival = convertToIntArray(arrivalTimes);
        int[] burst = convertToIntArray(burstTimes);
        int[] priority = (priorities != null) ? convertToIntArray(priorities) : new int[arrival.length];

        if (arrival.length != burst.length || (priorities != null && burst.length != priority.length)) {
            throw new IllegalArgumentException("Input arrays or lists must have the same size.");
        }

        Process[] processes = new Process[arrival.length];
        for (int i = 0; i < arrival.length; i++) {
            processes[i] = new Process(i + 1, arrival[i], burst[i], priority[i]);
        }
        return processes;
    }

    // Convert input to an integer array
    private int[] convertToIntArray(Object input) {
        if (input instanceof int[]) {
            return (int[]) input;
        } else if (input instanceof List) {
            List<?> list = (List<?>) input;
            int[] array = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = (Integer) list.get(i);
            }
            return array;
        } else {
            throw new IllegalArgumentException("Invalid input type. Must be int[] or List<Integer>.");
        }
    }

    // Set the scheduling strategy
    public void setStrategy(String strategyName) {
        switch (strategyName.toLowerCase(Locale.ROOT)) {
            case "fcfs":
                this.strategy = new FCFS();
                break;
            case "sjf":
                this.strategy = new SJF();
                break;
            case "priority":
                this.strategy = new PriorityScheduling();
                break;
            case "hrrn":
                this.strategy = new HRRN();
                break;
            case "ljf":
                this.strategy = new LJF();
                break;
            default:
                throw new IllegalArgumentException("Invalid strategy name: " + strategyName);
        }
    }

    // Set the scheduling strategy directly
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    // Method to set or update priorities
    public void setPriorities(Object priorities) {
        int[] priority = convertToIntArray(priorities);
        if (processes.length != priority.length) {
            throw new IllegalArgumentException("The length of priorities must match the existing processes.");
        }
        for (int i = 0; i < processes.length; i++) {
            processes[i].setPriority(priority[i]);
        }
    }

    // Method to set context switching delay
    public void setContextSwitchingDelay(int contextSwitchingDelay) {
        if (contextSwitchingDelay < 0) {
            throw new IllegalArgumentException("Context switching delay cannot be negative.");
        }
        this.contextSwitchingDelay = contextSwitchingDelay;
    }

    // Add a single process
    public void addProcess(int pid, int arrivalTime, int burstTime, Integer priority) {
        int processPriority = (priority != null) ? priority : 0;

        if (strategy instanceof PriorityScheduling && processPriority <= 0) {
            throw new IllegalArgumentException("Priority must be greater than 0 for Priority Scheduling.");
        }

        Process[] newProcesses = new Process[processes.length + 1];
        System.arraycopy(processes, 0, newProcesses, 0, processes.length);
        newProcesses[processes.length] = new Process(pid, arrivalTime, burstTime, processPriority);
        processes = newProcesses;
    }

    // Execute the selected scheduling strategy
    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy is not set. Please set a valid strategy before executing.");
        }
        if (processes.length == 0) {
            throw new IllegalStateException("No processes added. Please add processes before executing.");
        }

        // Ensure context switching delay is set to 0 if not provided
        if (contextSwitchingDelay < 0) {
            contextSwitchingDelay = 0;
        }

        strategy.execute(processes, contextSwitchingDelay);
    }

    // Reset all processes to their initial state
    public void reset() {
        for (Process process : processes) {
            process.reset();
        }
    }

    // Print the scheduling results
    public void printProcesses() {
        System.out.printf("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-10s%n",
                "PID", "Arrival Time", "Burst Time", "Priority", "Completion Time", "TAT", "Waiting Time", "Completed");
        for (Process process : processes) {
            System.out.printf("%-5d %-15d %-15d %-15d %-15d %-15d %-15d %-10s%n",
                    process.getPid(),
                    process.getArrivalTime(),
                    process.getBurstTime(),
                    process.getPriority(),
                    process.getCompletionTime(),
                    process.getTurnAroundTime(),
                    process.getWaitingTime(),
                    process.isCompleted() ? "Yes" : "No");
        }
    }

    // Performance Metrics
    public double efficiency() {
        int totalBurstTime = 0;
        int lastCompletionTime = 0;

        for (Process process : processes) {
            totalBurstTime += process.getBurstTime();
            lastCompletionTime = Math.max(lastCompletionTime, process.getCompletionTime());
        }

        return (double) totalBurstTime / lastCompletionTime;
    }

    public double throughput() {
        int totalProcesses = processes.length;
        int lastCompletionTime = 0;

        for (Process process : processes) {
            lastCompletionTime = Math.max(lastCompletionTime, process.getCompletionTime());
        }

        return (double) totalProcesses / lastCompletionTime;
    }

    public double averageWaitingTime() {
        int totalWaitingTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }

        return (double) totalWaitingTime / processes.length;
    }

    public double averageTurnaroundTime() {
        int totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnAroundTime();
        }

        return (double) totalTurnaroundTime / processes.length;
    }
}
