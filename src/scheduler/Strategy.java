package scheduler;

public interface Strategy {
    void execute(Process[] processes, int contextSwitchingDelay);
}
