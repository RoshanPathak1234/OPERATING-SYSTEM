package disk.Schedulers;
// import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class SSTF implements Strategy {
    private int totalHeadCount;
    private List<Integer> seekList;

    private int minSeekProcess(List<Integer> list ,int headPosition) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (Math.abs(list.get(i) - headPosition) < min)
                min = list.get(i);
        }
        return min;
    }

    @Override
    public int execute(List<Integer> requests, int headPosition) {
        totalHeadCount = 0;
        int currentHead = headPosition;
        List<Integer> copy = new ArrayList<>(requests);

        while(copy.size() > 0) {
            int next = minSeekProcess(copy, currentHead);
            totalHeadCount += next;
            seekList.add(next);
            currentHead = next;
            copy.remove(copy.indexOf(next));
        }
        return totalHeadCount;
    }

    @Override
    public List<Integer> seekSequence() {
        return seekList;
    }
}
