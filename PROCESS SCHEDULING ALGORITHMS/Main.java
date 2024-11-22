import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> Pid = new ArrayList<>();
        ArrayList<Integer> AT = new ArrayList<>();
        ArrayList<Integer> BT = new ArrayList<>();
        // CpuScheduling shedular = new CpuScheduling();

        

        Scanner scan = new Scanner(System.in);

        int i = 0;
        System.out.println("Enter the number of processes : ");
        int processes = scan.nextInt();

        System.out.println("Enter the Arrival time of Each Process : ");
        for( i = 0 ; i < processes ; i++) {
            AT.add(scan.nextInt());
        }
        
        System.out.println("Enter the Burst time of Each Process : ");
        for( i = 0 ; i < processes ; i++) {
            BT.add(scan.nextInt());
        }

        for( i = 0 ; i < processes ; i++) {
            Pid.add(i);
        }

        FCFS schedular = new FCFS( Pid , AT , BT );
        schedular.execute();
        System.out.println(schedular.getComplitionTime());
        System.out.println(schedular.getTotalTurnAroundTime());
        System.out.println(schedular.getWaitingTime());


        scan.close();
    }
}
