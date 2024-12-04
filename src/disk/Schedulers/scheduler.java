package disk.Schedulers;

import java.util.List;


/**
 * Represents a disk scheduler that manages disk I/O requests using various
 * scheduling strategies.
 * This class allows for the execution of different disk scheduling algorithms
 * based on the specified strategy.
 */
public class scheduler {

    private List<Integer> requests; // List of disk I/O requests
    private int headPosition; // Current position of the disk head
    private Strategy strategy; // Scheduling strategy to be used
    private int diskSize; // Size of the disk
    private int totalMovement; // Total movement of the disk head
    private List<Integer> seekSequence; // Sequence of disk seeks

    public static final int DEFAULT_HEAD_POSITION = 0; // Default head position
    public static final Strategy DEFAULT_STRATEGY = new FCFS(); // Default scheduling strategy

    /**
     * Constructs a scheduler with the specified strategy, disk size, requests, and
     * head position.
     *
     * @param strategy     the scheduling strategy to use
     * @param diskSize     the size of the disk
     * @param requests     the list of disk I/O requests
     * @param headPosition the initial position of the disk head
     * @throws IllegalArgumentException if requests is empty, diskSize is
     *                                  non-positive, or headPosition is out of
     *                                  bounds
     */
    public scheduler(Strategy strategy, int diskSize, List<Integer> requests, int headPosition) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Requests list cannot be null or empty.");
        }
        if (diskSize <= 0) {
            throw new IllegalArgumentException("Disk size must be greater than 0.");
        }
        if (headPosition < 0 || headPosition >= diskSize) {
            throw new IllegalArgumentException("Head position must be between 0 and disk size.");
        }
        this.requests = requests;
        this.headPosition = headPosition;
        this.strategy = strategy;
        this.diskSize = diskSize;
    }

    /**
     * Constructs a scheduler with the specified strategy name, disk size, requests,
     * and head position.
     *
     * @param strategyName the name of the scheduling strategy to use
     * @param diskSize     the size of the disk
     * @param requests     the list of disk I/O requests
     * @param headPosition the initial position of the disk head
     */
    public scheduler(String strategyName, int diskSize, List<Integer> requests, int headPosition) {
        this(DEFAULT_STRATEGY, diskSize, requests, headPosition);
        setStrategy(strategyName); // Set the strategy based on the name
    }

    /**
     * Constructs a scheduler with the specified disk size, requests, and head
     * position using the default strategy.
     *
     * @param diskSize     the size of the disk
     * @param requests     the list of disk I/O requests
     * @param headPosition the initial position of the disk head
     */
    public scheduler(int diskSize, List<Integer> requests, int headPosition) {
        this(DEFAULT_STRATEGY, diskSize, requests, headPosition);
    }

    /**
     * Constructs a scheduler with the specified requests and head position using
     * the default strategy.
     *
     * @param requests     the list of disk I/O requests
     * @param headPosition the initial position of the disk head
     */
    public scheduler(List<Integer> requests, int headPosition) {
        this(DEFAULT_STRATEGY, requests.size(), requests, headPosition);
    }

    /**
     * Constructs a scheduler with the specified requests and a default head
     * position using the default strategy.
     *
     * @param requests the list of disk I/O requests
     */
    public scheduler(List<Integer> requests) {
        this(requests, DEFAULT_HEAD_POSITION);
    }

    /**
     * Executes the disk scheduling algorithm based on the specified strategy.
     *
     * @throws IllegalCallerException if the strategy is not set
     */
    public void execute() {
        if (strategy == null) {
            throw new IllegalCallerException("Strategy is not set!");
        }
        totalMovement = strategy.execute(requests, headPosition);
    }

    /**
     * Executes the disk scheduling algorithm using the specified strategy.
     *
     * @param strategy the scheduling strategy to use
     */
    public void execute(Strategy strategy) {
        this.setStrategy(strategy);
        execute();
    }

    /**
     * Executes the disk scheduling algorithm using the specified strategy name.
     *
     * @param strategy the name of the scheduling strategy to use
     */
    public void execute(String strategy) {
        this.setStrategy(strategy);
        execute();
    }

    /**
     * Sets the scheduling strategy based on the provided strategy name.
     *
     * The following disk scheduling algorithms are supported:
     * <ul>
     * <li><b>FCFS</b> (First Come First Serve): Services requests in the order they
     * arrive.</li>
     * <li><b>SSTF</b> (Shortest Seek Time First): Services the request that is
     * closest to the current head position.</li>
     * <li><b>SCAN</b>: Moves the disk arm in one direction servicing requests until
     * it reaches the end, then reverses direction.</li>
     * <li><b>C-SCAN</b> (Circular SCAN): Similar to SCAN, but when the end is
     * reached, it jumps back to the beginning and continues servicing.</li>
     * <li><b>LOOK</b>: Similar to SCAN, but only goes as far as the last request in
     * each direction before reversing.</li>
     * <li><b>C-LOOK</b>: Similar to C-SCAN, but only goes as far as the last
     * request in each direction before jumping back to the first request.</li>
     * <li><b>RSS</b> (Random Scheduling): Services requests in a random order.</li>
     * <li><b>LIFO</b> (Last-In First-Out): Services the most recently added request
     * first.</li>
     * <li><b>N-STEP SCAN</b>: A variation of SCAN that services requests in
     * batches.</li>
     * <li><b>F-SCAN</b>: A variation of SCAN that uses two queues to manage
     * requests.</li>
     * </ul>
     *
     * @param strategy the name of the scheduling strategy to set(
     *                 <ul>
     *                 <li>fcfs</li>
     *                 <li>sstf</li>
     *                 <li>scan</li>
     *                 <li>cscan</li>
     *                 <li>look</li>
     *                 <li>clook</li>
     *                 <li>rss</li>
     *                 <li>lifo</li>
     *                 <li>nStepScan</li>
     *                 <li>fscan</li>
     *                 </ul>
     *                 )
     * 
     * @throws IllegalArgumentException      if the strategy name is null or empty
     * @throws UnsupportedOperationException if the strategy is not supported
     */
    public void setStrategy(String strategy) {
        if (strategy == null || strategy.isBlank()) {
            throw new IllegalArgumentException("Strategy can't be null or empty.");
        }
        switch (strategy.toLowerCase()) {
            case "fcfs":
                this.strategy = new FCFS();
                break;
            case "sstf":
                this.strategy = new SSTF();
                break;
            case "scan":
                this.strategy = new SCAN();
                break;
            case "cscan":
                this.strategy = new CSCAN();
                break;
            case "fscan":
                this.strategy = new FSCAN();
                break;
            case "rss":
                this.strategy = new RSS();
                break;
            case "nstepscan":
                this.strategy = new nStepScan();
                break;
            case "lifo":
                this.strategy = new LIFO();
                break;
            case "look":
                this.strategy = new LOOK();
                break;
            case "clook":
                this.strategy = new CLOOK();
                break;
            default:
                throw new UnsupportedOperationException("Strategy not supported.");
        }
    }

    /**
     * Sets the scheduling strategy directly using a Strategy object.
     *
     * @param strategy the scheduling strategy to set
     * @throws IllegalArgumentException if the strategy is null
     */
    public void setStrategy(Strategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy can't be null.");
        }
        this.strategy = strategy;
    }

    /**
     * Returns the total movement of the disk head after executing the scheduling
     * algorithm.
     *
     * @return the total movement of the disk head
     */
    public int getTotalMovement() {
        return totalMovement;
    }

    /**
     * Returns the sequence of disk seeks performed during the execution of the
     * scheduling algorithm.
     *
     * @return the list of disk seek positions
     */
    public List<Integer> getSeekSequence() {
        return seekSequence;
    }

    /**
     * Returns the current position of the disk head.
     *
     * @return the current head position
     */
    public int getHeadPosition() {
        return headPosition;
    }

    /**
     * Returns the list of disk I/O requests.
     *
     * @return the list of requests
     */
    public List<Integer> getRequests() {
        return requests;
    }

    /**
     * Returns the size of the disk.
     *
     * @return the size of the disk
     */
    public int getDiskSize() {
        return diskSize;
    }


}