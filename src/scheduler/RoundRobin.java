package scheduler;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The RoundRobin class implements the Round Robin scheduling strategy.
 * It assigns a fixed time quantum to each process in the ready queue.
 */
public class RoundRobin implements Strategy {
    private final int timeQuantum; // Time quantum for each process

    /**
     * Constructor to initialize the Round Robin scheduler with a specified time quantum.
     *
     * @param timeQuantum The time quantum for the Round Robin scheduling.
     */
    public RoundRobin(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        int n = processes.length;
        Queue<Process> queue = new LinkedList<>();
        
        // Initialize variables
        int currentTime = 0;
        int completedProcesses = 0;
        boolean[] isCompleted = new boolean[n];

        // Set initial remaining time for each process
        for (Process process : processes) {
            process.setRemainingTime(process.getBurstTime());
        }

        // Main loop until all processes are completed
        while (completedProcesses < n) {
            // Add all processes that have arrived by current time to the queue
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && !isCompleted[process.getPid() - 1]) {
                    if (!queue.contains(process)) {
                        queue.add(process);
                    }
                }
            }

            if (!queue.isEmpty()) {
                // Get the next process from the queue
                Process currentProcess = queue.poll();

                // Execute the process for the minimum of time quantum or remaining time
                int timeToExecute = Math.min(timeQuantum, currentProcess.getRemainingTime());
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeToExecute);
                currentTime += timeToExecute;

                // If the process is completed
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    currentProcess.setCompleted(true);
                    isCompleted[currentProcess.getPid() - 1] = true;
                    completedProcesses++;
                }

                // Add context switching delay if there are still processes in the queue
                if (!queue.isEmpty()) {
                    currentTime += contextSwitchingDelay;
                }
            } else {
                // If no process is ready, increment current time
                currentTime++;
            }
        }
    }
}