import java.util.ArrayList;

public class LJF implements Strategy {

    private ArrayList<Integer> sortedPid;
    private MaxHeap<Integer> maxHeap;

    private void computeCT(ArrayList<Integer> CT, ArrayList<Integer> AT, ArrayList<Integer> BT, int CSD) {
        int currentTime = 0; 
        for (int i = 0; i < sortedPid.size(); i++) {
            int pid = sortedPid.get(i); 
            
            if (AT.get(pid) > currentTime) {
                currentTime = AT.get(pid);
            }
    
            if (i > 0) {
                currentTime += CSD;
            }
    
            currentTime += BT.get(pid);
    
            CT.set(pid, currentTime);
        }
    }
    

    private void computeTAT(ArrayList<Integer> TAT , ArrayList<Integer> AT , ArrayList<Integer> CT) {
        for (int i = 0; i < CT.size(); i++) {
            TAT.set( sortedPid.get(i) , CT.get(i) - AT.get(i));
        }
    }

    private void computeWT( ArrayList<Integer> WT, ArrayList<Integer> TAT , ArrayList<Integer> BT) {

        for (int i = 0; i < TAT.size(); i++) {
            WT.set( sortedPid.get(i) , TAT.get(i) - BT.get(i));
        }
    }

    @Override
    public void execute(ArrayList<Integer> Pid , ArrayList<Integer> AT, ArrayList<Integer> BT, ArrayList<Integer> CT, ArrayList<Integer> TAT, ArrayList<Integer> WT , int CSD) {
        maxHeap = new MaxHeap<>(Pid , BT);
        sortedPid = maxHeap.heapSort();
        
        while (CT.size() < Pid.size()) {
            CT.add(0);  
        }
        while (TAT.size() < Pid.size()) {
            TAT.add(0); 
        }
        while (WT.size() < Pid.size()) {
            WT.add(0);  
        }

        computeCT(CT, TAT, BT, CSD);
        computeTAT(TAT, AT, CT);
        computeWT(WT, TAT, BT);
    
    }
}
