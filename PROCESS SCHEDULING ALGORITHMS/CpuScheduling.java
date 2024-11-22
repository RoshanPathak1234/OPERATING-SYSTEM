/*
** Algorithms
 * FCFS - First Come First Serve
 * SJF - Shortest Job First
 * LJF - Longest Job First
 * HRRN - Highest Response Ratio Next
 * SRTF - Shortest Remaining Time First
 * LRTF - Longest Remaining time First
 
** Important Short Names : 
 * Pid - Process id
 * AT - Arrival Time
 * CT - Completion Time
 * WT - Waiting Time
 * TAT - Turn Around Time
 * BT - Burst Time
 * TQ - Time Quantum
 * CSD - context Switching Delay 
 * th - Throughput
 * RT - Response Time
 * RR - Response Ratio
 * Efficiency - CPU Utiliazation
 */

import java.util.ArrayList;

public abstract class CpuScheduling {

    // private identifiers

    // protected identifiers

    protected ArrayList<Integer> Pid, AT, BT;
    protected ArrayList<Integer> TAT, CT , WT;
    protected ArrayList<Integer> sortedPid;
    protected int TQ, RT, CSD, TotalTAT , TotalWT , Efficiency;
    protected double RR, th , averageWT , averageTAT;

    // protected abstract identifiers

    protected abstract ArrayList<Integer> SortPid();
    protected abstract void computeTAT();
    protected abstract void computeWT();
    protected abstract void computeCT();

    // protected methods

    protected void nonPreEmptiveTAT() {
       
        for(int i = 0; i < sortedPid.size(); i++) {
            TAT.set(sortedPid.get(i) , CT.get(i) - BT.get(i));
        }
    }

    protected void nonPreEmptiveCT() {
        
        CT.set(sortedPid.get(0) , BT.get(sortedPid.get(0)));
        for(int i = 1; i < Pid.size(); i++) {
            CT.set(sortedPid.get(i) , CT.get(i-1) + BT.get(i));
        }
        
    }

    protected void nonPreEmptiveWT() {

        for(int i = 0; i < Pid.size(); i++) {
            WT.set(sortedPid.get(i) , TAT.get(i-1) - BT.get(i));
        }

    }

    protected void preEmptiveTAT() {

    }

    protected void preEmptiveWT() {

    }

    protected void preEmptiveCT() {
        
    }


    // private methods

    

    // constructors

    public CpuScheduling() {
        Pid = new ArrayList<>();
        AT = new ArrayList<>();
        BT = new ArrayList<>();
        TAT = new ArrayList<>();
        CT = new ArrayList<>();
        sortedPid = new ArrayList<>();

        TotalTAT = 0;
        TotalWT = 0;
        Efficiency = 0;
        th = 0;
        CSD = 0;
    }

    public CpuScheduling(ArrayList<Integer> Pid, ArrayList<Integer> AT, ArrayList<Integer> BT) {
        this.Pid = new ArrayList<>(Pid);
        this.AT = new ArrayList<>(AT);
        this.BT = new ArrayList<>(BT);
        TAT = new ArrayList<>();
        CT = new ArrayList<>();
        sortedPid = SortPid();

        TotalTAT = 0;
        TotalWT = 0;
        Efficiency = 0;
        th = 0;
        CSD = 0;
    }

    // public Methods

    public int getTotalTurnAroundTime() {

        for (int num : TAT) {
            TotalTAT += num;
        }

        return TotalTAT;

    }

    public ArrayList<Integer> getTurnAroundTime() {
        computeTAT();
        return TAT;
    }

    public double getAverageTurnAroundTime() {

        averageTAT = TotalTAT / Pid.size();

        return averageTAT;
    }

    public ArrayList<Integer> getWaitingTime() {
        computeWT();
        return WT;
    }

    public int getTotalWaitingTime() {

        for (int el : WT) {
            TotalWT += el;  
        }
        return TotalWT;
    }

    public ArrayList<Integer> getComplitionTime() {
        computeCT();
        return CT;
    }
    public double getAverageWaitingTime() {
        averageWT = TotalWT / Pid.size();

        return averageWT;
    }

    public double Throughput() {

        return th;

    }

    public int Efficiency() {

        int totalBT = 0;
        for (int num : BT) {
            totalBT += num;
        }

        Efficiency =  (totalBT / TotalTAT) * 100 ;

        return Efficiency;
    }

}
