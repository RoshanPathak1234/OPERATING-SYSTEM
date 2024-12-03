package scheduler;


/**
 * The LRTF class implements the Least Recently Used Time First scheduling strategy.
 * It selects the process that has not been executed for the longest time.
 */
public class LRTF implements Strategy {
    
    @Override
    public void execute(Process[] processes, int contextSwitchingDelay) {
        int n = processes.length;
        int currentTime = 0;
        int completedProcesses = 0;
        boolean[] isCompleted = new boolean[n];
        int[] lastExecutedTime = new int[n]; // Array to track the last execution time of each process

        // Set initial remaining time for each process
        for (Process process : processes) {
            process.setRemainingTime(process.getBurstTime());
        }

        while (completedProcesses < n) {
            // Find the process that has the least recently used time
            int lrtfIndex = -1;
            int maxWaitTime = -1;

            for (int i = 0; i < n; i++) {
                // Check if the process is not completed and has arrived
                if (!isCompleted[i] && processes[i].getArrivalTime() <= currentTime) {
                    // Calculate the wait time
                    int waitTime = currentTime - lastExecutedTime[i];
                    // Select the process with the maximum wait time
                    if (waitTime > maxWaitTime) {
                        maxWaitTime = waitTime;
                        lrtfIndex = i;
                    }
                }
            }

            if (lrtfIndex != -1) {
                Process currentProcess = processes[lrtfIndex];

                // Execute the current process for 1 time unit
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                lastExecutedTime[lrtfIndex] = currentTime; // Update last executed time
                currentTime++;

                // If the process is completed
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    currentProcess.setCompleted(true);
                    isCompleted[lrtfIndex] = true;
                    completedProcesses++;
                }
            } else {
                // If no process is ready, increment current time
                currentTime++;
            }

            // Add context switching delay if there are other processes in the queue
            if (completedProcesses < n) {
                currentTime += contextSwitchingDelay;
            }
        }
    }
}