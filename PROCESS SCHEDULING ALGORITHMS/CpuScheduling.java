import java.util.ArrayList;

public class CpuScheduling {
    private Strategy strategy;
    private ArrayList<Integer> Pid, AT, BT, TAT, CT, WT;

    public CpuScheduling() {
        this.Pid = new ArrayList<>();
        this.AT = new ArrayList<>();
        this.BT = new ArrayList<>();
        this.TAT = new ArrayList<>();
        this.CT = new ArrayList<>();
        this.WT = new ArrayList<>();
    }

    public CpuScheduling(Strategy strategy, ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.strategy = strategy;
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
    }
    public CpuScheduling(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        this.TAT = new ArrayList<>(Pid.size());
        this.CT = new ArrayList<>(Pid.size());
        this.WT = new ArrayList<>(Pid.size());
    }


    // public Methods

    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("No scheduling strategy set");
        }
        strategy.execute(AT, BT, CT, TAT, WT);
    }

    public void execute(Strategy strategy, ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.strategy = strategy;
        this.Pid = Pid;
        this.AT = AT;
        this.BT = BT;

        this.execute();
    }

    public void execute(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        
        this.Pid = Pid;
        this.AT = AT;
        this.BT = BT;
        
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

        avgWT = (double)avgWT / WT.size();
        return avgWT;
    }

    public double averageWTurnAroundTime() {
        double avgTAT = 0;
        for (int number : TAT) {
            avgTAT += number;
        }

        avgTAT = (double)avgTAT / TAT.size();
        return avgTAT;
    }
}
