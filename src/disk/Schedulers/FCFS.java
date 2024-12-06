package disk.Schedulers;

import java.util.List;

public class FCFS implements Strategy {

    List<Integer> seekList;

    @Override
    public int execute(List<Integer> requests, int headPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Integer> seekSequence() {
        return seekList;
    }
}
