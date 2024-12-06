package cpu.Schedulers;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The SRTF (Shortest Remaining Time First) class implements the preemptive scheduling strategy.
 * In this strategy, the process with the shortest remaining burst time is given priority.
 * If a new process arrives with a shorter remaining time, it preempts the currently running process.
 */
public class SRTF implements Strategy {

    /**
     * Executes the SRTF scheduling algorithm on the given processes.
     *
     * @param processes              An array of processes with arrival time, burst time, etc.
     * @param contextSwitchingDelay  The time delay incurred during context switching.
     */
    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        int n = processes.length;

        // Priority queue to select the process with the shortest remaining time
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime));

        // Initialize time and track completed processes
        int currentTime = 0;
        int completedProcesses = 0;

        // Set initial remaining time for each process and initialize completion status
        for (Process process : processes) {
            process.setRemainingTime(process.getBurstTime());
            process.setCompleted(false); // Ensure all processes are marked as incomplete
        }

        // Execute until all processes are completed
        while (completedProcesses < n) {
            // Add processes that have arrived by the current time to the queue
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && !process.isCompleted() && !queue.contains(process)) {
                    queue.add(process);
                }
            }

            if (!queue.isEmpty()) {
                // Select the process with the shortest remaining time
                Process currentProcess = queue.poll();

                // Execute the process for 1 time unit
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                currentTime++;

                // If the process is completed, update its metrics
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompleted(true);
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    completedProcesses++;
                } else {
                    // If the process is not completed, re-add it to the queue
                    queue.add(currentProcess);
                }

                // Add context switching delay if there are more processes in the queue
                if (!queue.isEmpty()) {
                    currentTime += contextSwitchingDelay;
                }
            } else {
                // If no process is ready, increment the current time
                currentTime++;
            }
        }
    }
}
