package cpu.Schedulers;

import java.util.Arrays;

public class PriorityScheduling implements Strategy {

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        // Sort processes by priority first, then by arrival time
        Arrays.sort(processes, (p1, p2) -> {
            if (p1.getPriority() == p2.getPriority()) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
            return Integer.compare(p1.getPriority(), p2.getPriority());
        });

        int currentTime = 0;

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];

            if (!process.isCompleted()) {
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

                // Calculate Turnaround Time (TAT) and Waiting Time (WT)
                int tat = process.getCompletionTime() - process.getArrivalTime();
                process.setTurnAroundTime(tat);

                int wt = process.getTurnAroundTime() - process.getBurstTime();
                process.setWaitingTime(wt);

                // Mark the process as completed
                process.setCompleted(true);
            }
        }
    }
}