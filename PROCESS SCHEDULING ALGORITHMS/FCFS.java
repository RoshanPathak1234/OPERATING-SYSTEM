import java.util.ArrayList;

public class FCFS implements Strategy {

    private ArrayList<Integer> sortedPid;

    private void computeCT(ArrayList<Integer> CT, ArrayList<Integer> AT, ArrayList<Integer> BT, int CSD) {
        int currentTime = 0;
        for (int i = 0; i < AT.size(); i++) {
            int pid = sortedPid.get(i);
            if (currentTime < AT.get(pid)) {
                currentTime = AT.get(pid); 
            }
            currentTime += BT.get(pid) + CSD;
            CT.set(pid, currentTime);
        }
    }

    private void computeTAT(ArrayList<Integer> TAT, ArrayList<Integer> AT, ArrayList<Integer> CT) {
        for (int i = 0; i < CT.size(); i++) {
            int pid = sortedPid.get(i);
            TAT.set(pid, CT.get(pid) - AT.get(pid));
        }
    }

    private void computeWT(ArrayList<Integer> WT, ArrayList<Integer> TAT, ArrayList<Integer> BT) {
        for (int i = 0; i < TAT.size(); i++) {
            int pid = sortedPid.get(i);
            WT.set(pid, TAT.get(pid) - BT.get(pid));
        }
    }

    @Override
    public void execute(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT, ArrayList<Integer> CT, ArrayList<Integer> TAT, ArrayList<Integer> WT, int CSD) {

        MinHeap<Integer> minHeap = new MinHeap<>(Pid, AT);
        sortedPid = minHeap.heapSort();

        while (CT.size() < Pid.size()) {
            CT.add(0);  
        }
        while (TAT.size() < Pid.size()) {
            TAT.add(0); 
        }
        while (WT.size() < Pid.size()) {
            WT.add(0);  
        }

        computeCT(CT, AT, BT, CSD);
        computeTAT(TAT, AT, CT);
        computeWT(WT, TAT, BT);
    }
}
