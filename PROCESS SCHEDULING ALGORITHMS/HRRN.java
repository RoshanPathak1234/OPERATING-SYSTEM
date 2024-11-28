import java.util.ArrayList;

public class HRRN implements Strategy {

    @Override
    public void execute(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT, ArrayList<Integer> CT, 
                        ArrayList<Integer> TAT, ArrayList<Integer> WT, int CSD) {

        int n = Pid.size();
        boolean[] completed = new boolean[n];
        int currentTime = CSD;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            int selectedProcess = -1;
            double maxResponseRatio = -1.0;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && AT.get(i) <= currentTime) {
                    int waitingTime = currentTime - AT.get(i);
                    double responseRatio = (double) (waitingTime + BT.get(i)) / BT.get(i);

                    if (responseRatio > maxResponseRatio) {
                        maxResponseRatio = responseRatio;
                        selectedProcess = i;
                    }
                }
            }

            if (selectedProcess == -1) {
                currentTime++;
                continue;
            }

            currentTime += BT.get(selectedProcess);
            CT.set(selectedProcess, currentTime); 
            TAT.set(selectedProcess, CT.get(selectedProcess) - AT.get(selectedProcess));
            WT.set(selectedProcess, TAT.get(selectedProcess) - BT.get(selectedProcess));

            completed[selectedProcess] = true;
            completedProcesses++;
        }
    }
}
