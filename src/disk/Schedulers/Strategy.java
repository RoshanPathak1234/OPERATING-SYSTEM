package disk.Schedulers;

import java.util.List;

/**
 * Interface for Disk Scheduling Strategies.
 * <p>
 * This interface defines the methods that any disk scheduling strategy must implement.
 * Implementing classes should provide specific algorithms for scheduling disk I/O requests.
 * </p>
 */
public interface Strategy {

    /**
     * Executes the disk scheduling algorithm.
     *
     * @param requests     A list of disk requests represented as integers.
     * @param headPosition The initial position of the disk head.
     * @return The total head movement as an integer, representing the sum of all movements made by the disk head.
     */
    int execute(List<Integer> requests, int headPosition);

    /**
     * Returns the sequence of disk seeks based on the scheduling algorithm.
     *
     * @return A list of integers representing the order of disk requests that were serviced.
     */
    List<Integer> seekSequence();
}