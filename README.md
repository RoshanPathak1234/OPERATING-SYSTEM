# CPU Scheduling Algorithms

This project provides a flexible framework to simulate and analyze various **CPU scheduling algorithms**. It implements popular scheduling strategies like **FCFS (First-Come, First-Serve)**, **SJF (Shortest Job First)**, and more. The design is modular, allowing for easy addition of new algorithms.

---

## Project Structure

### **Classes Overview**

1. **`CpuScheduling`**:
   - Manages process information (PID, Arrival Time, Burst Time, etc.).
   - Executes scheduling strategies using the `Strategy` interface.
   - Provides utility methods like average turnaround time, waiting time, efficiency, and throughput.

2. **`Strategy` (Interface)**:
   - Abstracts scheduling algorithms.
   - Methods to implement:
     ```java
     void execute(
         ArrayList<Integer> Pid,
         ArrayList<Integer> AT,
         ArrayList<Integer> BT,
         ArrayList<Integer> CT,
         ArrayList<Integer> TAT,
         ArrayList<Integer> WT,
         int CSD
     );
     ```

3. **Scheduling Algorithm Classes (e.g., `FCFS`, `SJF`)**:
   - Each class implements the `Strategy` interface.
   - Encapsulates the logic for a specific scheduling algorithm.

4. **`Main`**:
   - Handles user interaction.
   - Captures process data from the user and executes selected scheduling algorithms.
   - Displays results such as **Completion Time**, **Turnaround Time**, **Waiting Time**, etc.

---

## Features

- **Algorithms Supported**:
  - FCFS (First-Come, First-Serve)
  - SJF (Shortest Job First)
  - Additional algorithms (to be implemented) such as Round-Robin, Priority Scheduling, etc.
  
- **Metrics Calculated**:
  - **Completion Time (CT)**
  - **Turnaround Time (TAT)**
  - **Waiting Time (WT)**
  - **Average TAT** and **WT**
  - **CPU Efficiency**
  - **Throughput**

- **Modular Design**:
  - Easy to add new scheduling algorithms by implementing the `Strategy` interface.

---

## UML Class Diagram

```plaintext
+-------------------------+
|       Strategy          |
|-------------------------|
| + execute(...)          |
+-------------------------+
            ▲
            |
+-------------------------+          +-------------------------+
|     CpuScheduling       |          |      FCFS               |
|-------------------------|          |-------------------------|
| - strategy: Strategy    |          | + execute(...)          |
| - Pid, AT, BT, CT, TAT, |          +-------------------------+
|   WT, CSD               |
|-------------------------|          +-------------------------+
| + execute()             |          |      SJF                |
| + efficiency()          |          |-------------------------|
| + throughput()          |          | + execute(...)          |
+-------------------------+          +-------------------------+

                          +-------------------------+
                          |       Main              |
                          |-------------------------|
                          | + main(...)            |
                          +-------------------------+
```

---

## How to Use

### 1. **Run the Application**
   - Compile and run the `Main` class.
   - Use any Java IDE (e.g., IntelliJ IDEA, Eclipse) or run via the command line:
     ```bash
     javac Main.java
     java Main
     ```

### 2. **Input Format**
   - Enter the number of processes.
   - Input the **Arrival Time (AT)** and **Burst Time (BT)** for each process.

### 3. **Output**
   - Displays results for each algorithm:
     - Completion Time (CT)
     - Turnaround Time (TAT)
     - Waiting Time (WT)
     - Average TAT and WT
     - CPU Efficiency and Throughput.

### 4. **Adding a New Algorithm**
   - Implement the `Strategy` interface.
   - Define the logic in the `execute()` method.
   - Register the new strategy in the `Main` class for user selection.

---

## Example Workflow

### Input:
```plaintext
Enter the number of processes: 3
Enter the Arrival Time of each process:
0 2 4
Enter the Burst Time of each process:
5 3 1
```

### Output:
```plaintext
--- First-Come, First-Serve Scheduling ---
Completion Times: [5, 8, 9]
Turnaround Times: [5, 6, 5]
Waiting Times: [0, 3, 4]
Average Turnaround Time: 5.33
Average Waiting Time: 2.33
CPU Efficiency: 83%
CPU Throughput: 0.6
```

---

## Where to Use

This project is useful for:
- **Education**:
  - Understanding CPU scheduling algorithms and their metrics.
  - Experimenting with different strategies to observe their behavior.
  
- **Simulation**:
  - Simulating real-world process scheduling scenarios.
  - Comparing performance metrics (efficiency, throughput, etc.).

- **Development**:
  - Providing a base for extending scheduling strategies for research or industry-specific use cases.

---

## Future Scope

- Implement additional algorithms like:
  - Round Robin
  - Priority Scheduling (Preemptive/Non-Preemptive)
  - Multi-Level Queue Scheduling.
- Add support for **Gantt Charts** to visualize execution.
- Incorporate context-switching costs and overhead.

---

Let me know if you need further modifications or additions!
