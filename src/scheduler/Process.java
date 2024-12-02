package scheduler;

public class Process {
    private final int pid;
    private final int arrivalTime;
    private final int burstTime;
    private int priority;
    private int completionTime;
    private int turnAroundTime;
    private int waitingTime;
    private boolean completed;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public int getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void reset() {
        this.completionTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.completed = false;
    }
}
