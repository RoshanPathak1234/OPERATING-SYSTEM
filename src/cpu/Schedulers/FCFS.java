package cpu.Schedulers;

import java.util.Arrays;

/**
 * This class implements the First-Come, First-Served (FCFS) scheduling strategy.
 * FCFS is a non-preemptive scheduling algorithm where processes are executed
 * in the order they arrive in the ready queue.
 */
public class FCFS implements Strategy {

    /**
     * Executes the FCFS scheduling algorithm on the given array of processes.
     *
     * @param processes             An array of Process objects to be scheduled.
     * @param contextSwitchingDelay The delay time incurred during context switching
     *                               between processes.
     */
    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        // Sort processes based on their arrival time
        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));

        int currentTime = 0; // Initialize current time to 0
        for (Process process : processes) {
            // Update current time if the process arrives after the current time
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }

            // Add context switching delay if this is not the first process
            if (currentTime > 0) {
                currentTime += contextSwitchingDelay; 
            }

            // Execute the process
            currentTime += process.getBurstTime();
            process.setCompletionTime(currentTime);
            process.setCompleted(true);
            process.setTurnAroundTime(currentTime - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());
        }
    }
}