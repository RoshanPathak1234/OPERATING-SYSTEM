package cpu.Schedulers;

import java.util.List;
import java.util.Locale;

/**
 * The CpuScheduler class is responsible for managing and executing process scheduling strategies.
 * It initializes processes based on arrival times, burst times, and priorities, and allows
 * the user to set different scheduling strategies (e.g., FCFS, SJF, Priority Scheduling, HRRN, LJF).
 */
public class scheduler {

    private Strategy strategy;
    private Process[] processes;
    private int contextSwitchingDelay;

    /**
     * Constructor to initialize the CpuScheduler with arrival times, burst times, and priorities.
     * The context switching delay is set to 0 by default.
     *
     * @param arrivalTimes An array or list of arrival times for the processes.
     * @param burstTimes   An array or list of burst times for the processes.
     * @param priorities   An array or list of priorities for the processes.
     */
    public scheduler(Object arrivalTimes, Object burstTimes, Object priorities) {
        this.contextSwitchingDelay = 0; // Default to 0
        this.processes = initializeProcesses(arrivalTimes, burstTimes, priorities);
    }

    /**
     * Constructor to initialize the CpuScheduler with arrival times, burst times, priorities,
     * and a context switching delay.
     *
     * @param arrivalTimes         An array or list of arrival times for the processes.
     * @param burstTimes           An array or list of burst times for the processes.
     * @param priorities           An array or list of priorities for the processes.
     * @param contextSwitchingDelay The delay incurred during context switching between processes.
     */
    public scheduler(Object arrivalTimes, Object burstTimes, Object priorities, int contextSwitchingDelay) {
        this.contextSwitchingDelay = contextSwitchingDelay;
        this.processes = initializeProcesses(arrivalTimes, burstTimes, priorities);
    }

    /**
     * Initializes the processes based on the provided arrival times, burst times, and priorities.
     *
     * @param arrivalTimes An array or list of arrival times.
     * @param burstTimes   An array or list of burst times.
     * @param priorities   An array or list of priorities.
     * @return An array of Process objects initialized with the provided parameters.
     * @throws IllegalArgumentException if the input arrays or lists do not have the same size.
     */
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

    /**
     * Converts the input to an integer array.
     *
     * @param input The input to convert, which can be an int[] or a List<Integer>.
     * @return An integer array containing the converted values.
     * @throws IllegalArgumentException if the input type is invalid.
     */
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

    /**
     * Sets the scheduling strategy based on the provided strategy name.
     *
     * @param strategyName The name of the scheduling strategy (e.g., "fcfs", "sjf", "priority", "hrrn", "ljf").
     * @throws IllegalArgumentException if the strategy name is invalid.
     */
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
            case "srtf" :
                 this.strategy = new SRTF();
                 break;
            case "lrtf" :
                 this.strategy = new LRTF();
                 break;
            default:
                throw new IllegalArgumentException("Invalid strategy name: " + strategyName);
        }
    }

    /**
     * Sets the scheduling strategy directly using a Strategy object.
     *
     * @param strategy The Strategy object to be used for scheduling.
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Updates the priorities of the existing processes.
     *
     * @param priorities An array or list of new priorities for the processes.
     * @throws IllegalArgumentException if the length of priorities does not match the number of processes.
     */
    public void setPriorities(Object priorities) {
        int[] priority = convertToIntArray(priorities);
        if (processes.length != priority.length) {
            throw new IllegalArgumentException("The length of priorities must match the existing processes.");
        }
        for (int i = 0; i < processes.length; i++) {
            processes[i].setPriority(priority[i]);
        }
    }

    /**
     * Sets the context switching delay.
     *
     * @param contextSwitchingDelay The delay incurred during context switching.
     * @throws IllegalArgumentException if the context switching delay is negative.
     */
    public void setContextSwitchingDelay(int contextSwitchingDelay) {
        if (contextSwitchingDelay < 0) {
            throw new IllegalArgumentException("Context switching delay cannot be negative.");
        }
        this.contextSwitchingDelay = contextSwitchingDelay;
    }

    /**
     * Adds a new process to the scheduler.
     *
     * @param pid           The process ID.
     * @param arrivalTime   The arrival time of the process.
     * @param burstTime     The burst time of the process.
     * @param priority      The priority of the process (can be null).
     * @throws IllegalArgumentException if the priority is not greater than 0 for Priority Scheduling.
     */
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

    /**
     * Executes the selected scheduling strategy.
     *
     * @throws IllegalStateException if the strategy is not set or no processes are added.
     */
    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy is not set. Please set a valid strategy before executing.");
        }
        if (processes.length == 0) {
            throw new IllegalStateException("No processes added. Please add processes before executing.");
        }

        strategy.execute(processes, contextSwitchingDelay);
    }

    /**
     * Resets all processes to their initial state.
     */
    public void reset() {
        for (Process process : processes) {
            process.reset();
        }
    }

    /**
     * Prints the scheduling results in a formatted table.
     */
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

    /**
     * Calculates the efficiency of the scheduling.
     *
     * @return The efficiency as a double value.
     */
    public double efficiency() {
        int totalBurstTime = 0;
        int lastCompletionTime = 0;

        for (Process process : processes) {
            totalBurstTime += process.getBurstTime();
            lastCompletionTime = Math.max(lastCompletionTime, process.getCompletionTime());
        }

        return (double) totalBurstTime / lastCompletionTime;
    }

    /**
     * Calculates the throughput of the scheduling.
     *
     * @return The throughput as a double value.
     */
    public double throughput() {
        int totalProcesses = processes.length;
        int lastCompletionTime = 0;

        for (Process process : processes) {
            lastCompletionTime = Math.max(lastCompletionTime, process.getCompletionTime());
        }

        return (double) totalProcesses / lastCompletionTime;
    }

    /**
     * Calculates the average waiting time of the processes.
     *
     * @return The average waiting time as a double value.
     */
    public double averageWaitingTime() {
        int totalWaitingTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }

        return (double) totalWaitingTime / processes.length;
    }

    /**
     * Calculates the average turnaround time of the processes.
     *
     * @return The average turnaround time as a double value.
     */
    public double averageTurnaroundTime() {
        int totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnAroundTime();
        }

        return (double) totalTurnaroundTime / processes.length;
    }

    /**
     * Calculates the CPU utilization based on the total burst time and the total time the CPU was busy.
     *
     * @return The CPU utilization as a percentage.
     */
    public double cpuUtilization() {
        int totalBurstTime = 0;
        int lastCompletionTime = 0;

        for (Process process : processes) {
            totalBurstTime += process.getBurstTime();
            lastCompletionTime = Math.max(lastCompletionTime, process.getCompletionTime());
        }

        return (lastCompletionTime > 0) ? (double) totalBurstTime / lastCompletionTime * 100 : 0;
    }
}