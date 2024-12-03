package scheduler;

/**
 * The Process class represents a process in a process scheduling system.
 * It contains information about the process's ID, arrival time, burst time,
 * priority, and various timing metrics. This class is used to manage and
 * track the state of a process during scheduling.
 */
public class Process {
    private final int pid;           // Process ID
    private final int arrivalTime;    // Time at which the process arrives in the ready queue
    private final int burstTime;      // Time required for the process to complete execution
    private int priority;             // Priority of the process
    private int completionTime;       // Time at which the process completes execution
    private int turnAroundTime;       // Total time taken from arrival to completion
    private int remainingTime;        // Remaining time for the process to complete execution
    private int waitingTime;          // Total time the process has been in the ready queue
    private boolean completed;        // Indicates whether the process has completed execution

    /**
     * Constructs a new Process with the specified parameters.
     *
     * @param pid           The unique identifier for the process.
     * @param arrivalTime   The time at which the process arrives in the ready queue.
     * @param burstTime     The time required for the process to execute.
     * @param priority      The priority level of the process.
     */
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime; // Initialize remaining time to burst time
        this.completed = false;          // Initialize as not completed
    }

    /**
     * Returns the process ID.
     *
     * @return The unique identifier for the process.
     */
    public int getPid() {
        return pid;
    }

    /**
     * Returns the arrival time of the process.
     *
     * @return The time at which the process arrives in the ready queue.
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns the burst time of the process.
     *
     * @return The time required for the process to complete execution.
     */
    public int getBurstTime() {
        return burstTime;
    }

    /**
     * Returns the priority of the process.
     *
     * @return The priority level of the process.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns the completion time of the process.
     *
     * @return The time at which the process completes execution.
     */
    public int getCompletionTime() {
        return completionTime;
    }

    /**
     * Returns the turnaround time of the process.
     *
     * @return The total time taken from arrival to completion.
     */
    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    /**
     * Returns the remaining time of the process.
     *
     * @return The remaining time for the process to complete execution.
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Returns the waiting time of the process.
     *
     * @return The total time the process has been in the ready queue.
     */
    public int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Checks if the process has completed execution.
     *
     * @return true if the process is completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion time of the process.
     *
     * @param completionTime The time at which the process completes execution.
     */
    void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * Sets the turnaround time of the process.
     *
     * @param turnAroundTime The total time taken from arrival to completion.
     */
    void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    /**
     * Sets the remaining time for the process.
     *
     * @param time The remaining time for the process to complete execution.
     */
    public void setRemainingTime(int time) {
        this.remainingTime = time;
    }

    /**
     * Sets the waiting time of the process.
     *
     * @param waitingTime The total time the process has been in the ready queue.
     */
    void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Sets the priority of the process.
     *
     * @param priority The new priority level of the process.
     */
    void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Sets the completion status of the process.
     *
     * @param completed true if the process is completed, false otherwise.
     */
    void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Resets the process to its initial state.
     * This method clears the completion time, turnaround time,
     * waiting time, and marks the process as not completed.
     */
    public void reset() {
        this.completionTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.completed = false;
        this.remainingTime = burstTime; // Reset remaining time to burst time
    }
}