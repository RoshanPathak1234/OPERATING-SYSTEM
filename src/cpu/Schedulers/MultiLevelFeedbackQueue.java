package cpu.Schedulers;

import java.util.*;

/**
 * MultiLevelFeedbackQueue with configurable number of queues and algorithms.
 */
public class MultiLevelFeedbackQueue implements Strategy {
    private final ArrayList<Queue<Process>> queues; // Queues for each priority level
    private final List<Strategy> strategies;       // Strategies for each queue
    private final int[] timeQuantums;              // Time quantums for each queue
    private final Set<Process> processesInQueues;  // Tracks processes already in queues

    /**
     * Constructor for MultiLevelFeedbackQueue using List and Array parameters.
     *
     * @param numQueues     Number of priority levels (queues).
     * @param strategies    List of scheduling strategies for each queue.
     * @param timeQuantums  Array of time quantum for each queue. Use `Integer.MAX_VALUE` for no time slicing.
     */
    public MultiLevelFeedbackQueue(int numQueues, List<Strategy> strategies, int[] timeQuantums) {
        if (numQueues != strategies.size() || numQueues != timeQuantums.length) {
            throw new IllegalArgumentException("Number of queues, strategies, and time quantums must match.");
        }

        this.queues = new ArrayList<>();
        this.strategies = new ArrayList<>(strategies);
        this.timeQuantums = timeQuantums.clone();
        this.processesInQueues = new HashSet<>();

        for (int i = 0; i < numQueues; i++) {
            this.queues.add(new LinkedList<>());
        }
    }

    /**
     * Constructor for MultiLevelFeedbackQueue using Array parameters.
     *
     * @param numQueues     Number of priority levels (queues).
     * @param strategies    Array of scheduling strategies for each queue.
     * @param timeQuantums  Array of time quantum for each queue. Use `Integer.MAX_VALUE` for no time slicing.
     */
    public MultiLevelFeedbackQueue(int numQueues, Strategy[] strategies, int[] timeQuantums) {
        this(numQueues, List.of(strategies), timeQuantums);
    }

    /**
     * Constructor for MultiLevelFeedbackQueue with default time quantums.
     *
     * @param numQueues     Number of priority levels (queues).
     * @param strategies    Array of scheduling strategies for each queue.
     */
    public MultiLevelFeedbackQueue(int numQueues, Strategy[] strategies) {
        this(numQueues, strategies, new int[numQueues]);
        for (int i = 0; i < numQueues; i++) {
            this.timeQuantums[i] = Integer.MAX_VALUE; // Default to no time slicing
        }
    }

    /**
     * Constructor for MultiLevelFeedbackQueue using mixed parameters.
     *
     * @param numQueues     Number of priority levels (queues).
     * @param strategies    List of scheduling strategies for each queue.
     * @param timeQuantums  List of time quantum for each queue. Use `Integer.MAX_VALUE` for no time slicing.
     */
    public MultiLevelFeedbackQueue(int numQueues, List<Strategy> strategies, List<Integer> timeQuantums) {
        this(numQueues, strategies, timeQuantums.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        int n = processes.length;
        int currentTime = 0;
        int completedProcesses = 0;

        // Initialize processes
        for (Process process : processes) {
            if (process.getRemainingTime() == 0) {
                process.setRemainingTime(process.getBurstTime());
                process.setCompleted(false);
            }
        }

        while (completedProcesses < n) {
            // Add newly arrived processes to the highest-priority queue (queue 0)
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && !process.isCompleted() && !isInAnyQueue(process)) {
                    queues.get(0).add(process);
                    processesInQueues.add(process);
                }
            }

            // Find the first non-empty queue
            Process currentProcess = null;
            int currentQueueIndex = -1;

            for (int i = 0; i < queues.size(); i++) {
                if (!queues.get(i).isEmpty()) {
                    currentProcess = queues.get(i).poll();
                    processesInQueues.remove(currentProcess);
                    currentQueueIndex = i;
                    break;
                }
            }

            if (currentProcess != null) {
                // Execute the process using the queue's assigned strategy
                Strategy strategy = strategies.get(currentQueueIndex);
                Queue<Process> singleProcessQueue = new LinkedList<>();
                singleProcessQueue.add(currentProcess);

                // Execute for the time quantum or remaining time
                int timeSlice = Math.min(currentProcess.getRemainingTime(), timeQuantums[currentQueueIndex]);
                strategy.execute(singleProcessQueue.toArray(new Process[0]), 0); // Pass context delay as 0

                // Update process timing and status
                currentTime += timeSlice;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeSlice);

                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompleted(true);
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    completedProcesses++;
                } else if (currentQueueIndex + 1 < queues.size()) {
                    queues.get(currentQueueIndex + 1).add(currentProcess);
                    processesInQueues.add(currentProcess);
                } else {
                    queues.get(currentQueueIndex).add(currentProcess);
                    processesInQueues.add(currentProcess);
                }

                // Add context switching delay if applicable
                if (!queues.get(currentQueueIndex).isEmpty() || completedProcesses < n) {
                    currentTime += contextSwitchingDelay;
                }
            } else {
                // If no process is ready, increment current time
                currentTime++;
            }
        }
    }

    /**
     * Checks if a process is already in any of the queues.
     *
     * @param process The process to check.
     * @return True if the process is in any queue, false otherwise.
     */
    private boolean isInAnyQueue(Process process) {
        return processesInQueues.contains(process);
    }
}
