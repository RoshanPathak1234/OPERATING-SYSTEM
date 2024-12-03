package scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The SRTF class implements the Shortest Remaining Time First scheduling strategy.
 * It preempts the currently running process if a new process arrives with a shorter remaining time.
 */
public class SRTF implements Strategy {

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        int n = processes.length;

        // Create a priority queue to hold processes based on remaining time
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));

        // Initialize variables
        int currentTime = 0;
        int completedProcesses = 0;
        boolean[] isCompleted = new boolean[n];

        // Set the initial remaining time for each process
        for (Process process : processes) {
            process.setRemainingTime(process.getBurstTime());
        }

        while (completedProcesses < n) {
            // Add all processes that have arrived by current time to the queue
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && !isCompleted[process.getPid() - 1]) {
                    queue.add(process);
                }
            }

            if (!queue.isEmpty()) {
                // Get the process with the shortest remaining time
                Process currentProcess = queue.poll();

                // Execute the current process for 1 time unit
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                currentTime++;

                // If the process is completed
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    isCompleted[currentProcess.getPid() - 1] = true;
                    completedProcesses++;
                }

                // Add context switching delay if there are other processes in the queue
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