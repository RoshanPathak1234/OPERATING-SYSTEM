package scheduler;

import java.util.Arrays;

public class SJF implements Strategy {

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        // Sort processes in ascending order of burst time (Shortest Job First)
        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));

        int currentTime = 0;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];

            // If the process arrives after the current time, set current time to arrival time
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }

            // Add context switching delay if this is not the first process
            if (i > 0) {
                currentTime += contextSwitchingDelay;
            }

            // Execute the process
            currentTime += process.getBurstTime();
            process.setCompletionTime(currentTime);
            process.setCompleted(true);
        }

        // Calculate Turnaround Time (TAT) and Waiting Time (WT)
        for (Process process : processes) {
            int tat = process.getCompletionTime() - process.getArrivalTime();
            process.setTurnAroundTime(tat);

            int wt = process.getTurnAroundTime() - process.getBurstTime();
            process.setWaitingTime(wt);
        }
    }
}
