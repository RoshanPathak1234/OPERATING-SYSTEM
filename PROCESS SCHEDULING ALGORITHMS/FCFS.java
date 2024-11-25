import java.util.ArrayList;

public class FCFS implements Strategy {

    @Override
    public void execute(ArrayList<Integer> AT, ArrayList<Integer> BT, ArrayList<Integer> CT, ArrayList<Integer> TAT, ArrayList<Integer> WT) {
        int currentTime = 0;

        for (int i = 0; i < AT.size(); i++) {
            if (currentTime < AT.get(i)) {
                currentTime = AT.get(i); 
            }
            currentTime += BT.get(i);
            CT.add(currentTime);
        }

        
        for (int i = 0; i < CT.size(); i++) {
            TAT.add(CT.get(i) - AT.get(i));
        }

        
        for (int i = 0; i < TAT.size(); i++) {
            WT.add(TAT.get(i) - BT.get(i));
        }
    }
}
