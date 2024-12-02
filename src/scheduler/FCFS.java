package scheduler;

import java.util.Arrays;

public class FCFS implements Strategy {

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));

        int currentTime = 0;
        for (Process process : processes) {
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }

            if (currentTime > 0) {
                currentTime += contextSwitchingDelay; 
            }

            currentTime += process.getBurstTime();
            process.setCompletionTime(currentTime);
            process.setCompleted(true);
            process.setTurnAroundTime(currentTime - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getBurstTime());
        }
    }
}
