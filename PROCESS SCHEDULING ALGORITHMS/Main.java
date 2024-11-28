import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> Pid = new ArrayList<>();
        ArrayList<Integer> AT = new ArrayList<>();
        ArrayList<Integer> BT = new ArrayList<>();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        int processes = scan.nextInt();

        System.out.println("Enter the Arrival Time of each process:");
        for (int i = 0; i < processes; i++) {
            AT.add(scan.nextInt());
        }

        System.out.println("Enter the Burst Time of each process:");
        for (int i = 0; i < processes; i++) {
            BT.add(scan.nextInt());
        }

        for (int i = 0; i < processes; i++) {
            Pid.add(i); 
        }

        // FCFS Scheduling
        System.out.println("\n--- First-Come, First-Serve Scheduling ---\n");
        CpuScheduling fcfsScheduler = new CpuScheduling(new FCFS(), Pid, AT, BT);
        fcfsScheduler.execute();
        displayResults(fcfsScheduler);

        // SJF Scheduling
        System.out.println("\n--- Shortest Job First Scheduling ---\n");
        CpuScheduling sjfScheduler = new CpuScheduling(new SJF(), Pid, AT, BT);
        sjfScheduler.execute();
        displayResults(sjfScheduler);

        // LJF Scheduling
        System.out.println("\n--- Longest Job First Scheduling ---\n");
        CpuScheduling ljfScheduler = new CpuScheduling(new LJF(), Pid, AT, BT);
        ljfScheduler.execute();
        displayResults(ljfScheduler);

        scan.close();
    }

    // Helper method to display results
    private static void displayResults(CpuScheduling scheduler) {
        System.out.println("Completion Times: " + scheduler.getCompletionTime());
        System.out.println("Turnaround Times: " + scheduler.getTurnAroundTime());
        System.out.println("Waiting Times: " + scheduler.getWaitingTime());
        System.out.println("Average Turnaround Time: " + scheduler.averageTurnAroundTime());
        System.out.println("Average Waiting Time: " + scheduler.averageWaitingTime());
        System.out.println("CPU Efficiency: " + scheduler.efficiency() + "%");
        System.out.println("CPU throughput: " + scheduler.throughput());
    }
}
