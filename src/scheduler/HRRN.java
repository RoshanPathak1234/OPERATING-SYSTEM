package scheduler;

public class HRRN implements Strategy {

    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        int currentTime = 0;

        while (true) {
            int selectedProcess = -1;
            double highestResponseRatio = -1;

            // Find the process with the highest response ratio
            for (int i = 0; i < processes.length; i++) {
                Process process = processes[i];
                if (!process.isCompleted() && process.getArrivalTime() <= currentTime) {
                    // Calculate response ratio: (WT + BT) / BT
                    int waitingTime = currentTime - process.getArrivalTime();
                    double responseRatio = (double) (waitingTime + process.getBurstTime()) / process.getBurstTime();

                    if (responseRatio > highestResponseRatio) {
                        highestResponseRatio = responseRatio;
                        selectedProcess = i;
                    }
                }
            }

            if (selectedProcess == -1) {
                // No process can be selected, break the loop
                break;
            }

            // Execute the selected process
            Process selected = processes[selectedProcess];
            currentTime = Math.max(currentTime, selected.getArrivalTime());
            currentTime += selected.getBurstTime();
            selected.setCompletionTime(currentTime);

            // Calculate Turnaround Time (TAT) and Waiting Time (WT)
            int tat = selected.getCompletionTime() - selected.getArrivalTime();
            selected.setTurnAroundTime(tat);

            int wt = selected.getTurnAroundTime() - selected.getBurstTime();
            selected.setWaitingTime(wt);

            // Mark the process as completed
            selected.setCompleted(true);
        }
    }
}
