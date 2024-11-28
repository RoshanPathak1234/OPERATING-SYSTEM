import java.util.ArrayList;
import java.util.Collections;

public class CpuScheduling {
    private Strategy strategy;
    private ArrayList<Integer> Pid, AT, BT, TAT, CT, WT;
    private int CSD;

    // constructors

    public CpuScheduling() {
        this.CSD = 0;
    }

    public CpuScheduling(Strategy strategy, ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.strategy = strategy;
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
        this.CSD = 0;

        Collections.fill(CT, 0);
        Collections.fill(TAT, 0);
        Collections.fill(WT, 0);
    }

    public CpuScheduling(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
        this.CSD = 0;

        Collections.fill(CT, 0);
        Collections.fill(TAT, 0);
        Collections.fill(WT, 0);
    }

    public CpuScheduling(Strategy strategy, ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT,
            int CSD) {
        this.strategy = strategy;
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
        this.CSD = CSD;

        Collections.fill(CT, 0);
        Collections.fill(TAT, 0);
        Collections.fill(WT, 0);
    }

    public CpuScheduling(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT, int CSD) {
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
        this.CSD = CSD;

        Collections.fill(CT, 0);
        Collections.fill(TAT, 0);
        Collections.fill(WT, 0);
    }

    // public Methods

    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("No scheduling strategy set");
        }
        strategy.execute(Pid, AT, BT, CT, TAT, WT, CSD);
    }

    public void execute(Strategy strategy, ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.strategy = strategy;
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
        this.strategy = strategy;

        this.CSD = 0;
        this.Pid = Pid;
        this.AT = AT;
        this.BT = BT;

        this.execute();
    }

    public void execute(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());

        this.CSD = 0;
        this.Pid = Pid;
        this.AT = AT;
        this.BT = BT;

        this.execute();
    }

    public void execute(Strategy strategy, ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT,int CSD) {

        this.strategy = strategy;
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
        this.CSD = CSD;

        this.execute();
    }

    public void execute(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT, int CSD) {

        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());

        this.Pid = Pid;
        this.AT = AT;
        this.BT = BT;
        this.CSD = CSD;

        this.execute();
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy(Strategy strategy) {
        return this.strategy;
    }

    public ArrayList<Integer> getCompletionTime() {
        return CT;
    }

    public ArrayList<Integer> getTurnAroundTime() {
        return TAT;
    }

    public ArrayList<Integer> getWaitingTime() {
        return WT;
    }

    public double averageWaitingTime() {
        double avgWT = 0;
        for (int number : WT) {
            avgWT += number;
        }

        avgWT = (double) avgWT / WT.size();
        return avgWT;
    }

    public double averageTurnAroundTime() {
        double avgTAT = 0;
        for (int number : TAT) {
            avgTAT += number;
        }

        avgTAT = (double) avgTAT / TAT.size();
        return avgTAT;
    }

    public int efficiency() {
        
        if (Pid.isEmpty() || BT.isEmpty() || AT.isEmpty() || CT.isEmpty()) {
            return 0;
        }
    
        int usefulTime = 0;
        for (int bt : BT) {
            usefulTime += bt;
        }

        int firstArrivalTime = Collections.min(AT); 
        int lastCompletionTime = Collections.max(CT); 
        int totalTime = lastCompletionTime - firstArrivalTime;
           
        if (totalTime <= 0) {
            return 0;
        }
    
        int efficiency = (int) Math.round(((double) usefulTime / totalTime) * 100);
    
        return efficiency;
    }
    

    public double throughput() {
        if (CT.isEmpty() || AT.isEmpty()) {
            return 0.0;
        }
    
        int firstArrivalTime = Collections.min(AT); 
        int lastCompletionTime = Collections.max(CT); 
    
        int totalTimeSpan = lastCompletionTime - firstArrivalTime;
    
        if (totalTimeSpan <= 0) {
            return 0.0;
        }
    
        double throughput = (double) CT.size() / totalTimeSpan;
    
        return Math.round(throughput * 100.0) / 100.0;
    }
    

}
