package disk.Schedulers;

import java.util.List;

public class RSS implements Strategy{

    @Override
    public int execute(List<Integer> requests, int headPosition) {
        return 0;
    }

    @Override
    public List<Integer> seekSequence() {
        // TODO Auto-generated method stub
        return null;
    }
}