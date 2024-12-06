package cpu.Schedulers;

/**
 * The Strategy interface defines a contract for different scheduling algorithms
 * in a process scheduling system. Implementing classes must provide a specific
 * scheduling strategy by defining the execution logic for the provided processes.
 */
public interface Strategy {
    
    /**
     * Executes the scheduling algorithm on the provided array of processes.
     * This method should implement the logic to schedule the processes based
     * on a specific algorithm (e.g., First-Come, First-Served, Round Robin, etc.).
     *
     * @param processes             An array of Process objects to be scheduled.
     *                               The order of processes in this array may
     *                               affect the scheduling outcome based on the
     *                               chosen algorithm.
     * @param contextSwitchingDelay The delay incurred during context switching
     *                               between processes. This value should be
     *                               accounted for in the scheduling logic to
     *                               accurately calculate completion, turnaround,
     *                               and waiting times for each process.
     */
    void execute(Process[] processes, int contextSwitchingDelay);
}