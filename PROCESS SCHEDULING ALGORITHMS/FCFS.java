import java.util.ArrayList;

public class FCFS extends CpuScheduling {
    MinHeap<Integer> heap;

    // constructors

    public FCFS() {
        super();
    }

    public FCFS(ArrayList<Integer> Pid , ArrayList<Integer> AT, ArrayList<Integer> BT) {
        super(Pid , AT , BT);
    }

    // Abstract Methods Overriding

    @Override
    protected ArrayList<Integer> SortPid() {
       heap = new MinHeap<Integer>(this.Pid , this.AT);
       return heap.heapSort();
    }

    protected void computeTAT() {
        this.nonPreEmptiveTAT();
    }

    protected void computeCT() {
        this.nonPreEmptiveCT();
    }

    protected void computeWT() {
        this.nonPreEmptiveWT();
    }
     
}