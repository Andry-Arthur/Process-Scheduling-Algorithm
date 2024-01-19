import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobinScheduler extends Scheduler {
	int quantum;

	public RoundRobinScheduler(File file) {
		super(file);
		quantum = 20;
		name = "Round Robin q=" + quantum;
	}

	public RoundRobinScheduler(File file, int q) {
		super(file);
		quantum = q;
		name =  "Round Robin q=" + quantum;
	}

	@Override
	public void run(){ 
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
			nProcess = scanner.nextInt();

			//load the processes
			Queue<ProcessInfo> processes = new LinkedList<>();
			for(int i = 0; i < nProcess; ++i) {
				int id = scanner.nextInt();
				int priority = scanner.nextInt();
				int arrival = scanner.nextInt();
				int burst = scanner.nextInt();

				processes.add(new ProcessInfo(id, priority, arrival, burst));				
			}
			scanner.close();

			if(processes.isEmpty()) {
				System.err.println("Process is Empty");
				return;
			} 

			ProcessInfo onCpu = null;
			Queue<ProcessInfo> ready = new LinkedList<>();
			processed = new ArrayList<>();
			int currQ = 0;
			while(!processes.isEmpty() || !ready.isEmpty() || onCpu != null) {
				ProcessInfo head = processes.peek();
				if(head != null  && head.arrivalTime <= cumulTime) {
					head = processes.poll();
					ready.add(head);
				}

				if(onCpu == null) {
					cumulTime+= CONTEXT_SWITCH;
					onCpu = ready.poll();
					onCpu.waitTime += CONTEXT_SWITCH;
					currQ = 0;
					for(ProcessInfo p:ready) {
						p.waitTime+= CONTEXT_SWITCH;
					}
				}
				else {
					onCpu.burstTime--;
					currQ++;

					if(onCpu.burstTime == 0) {
						onCpu.completionTime = (int) cumulTime;
						onCpu.turnAroundTime = onCpu.completionTime - onCpu.arrivalTime;
						processed.add(onCpu);
						onCpu = null;
					}
					else if(currQ == quantum) {
						ready.add(onCpu);
						onCpu = null;
						currQ = 0;
					}
				}
				for(ProcessInfo p:ready) {
					p.waitTime++;
				}

				cumulTime++;
			}

			Collections.sort(processed, new idComparator());

			for(ProcessInfo p: processed) {
				cpuPerc += p.burstCounter;
				avgWaitTime += p.waitTime;
				avgTurnaroundTime += p.turnAroundTime;
			}
			lastProcTime = (int) cumulTime;
			cpuPerc = cpuPerc/lastProcTime * 100;
			throughput = nProcess/ (lastProcTime/1000.0);
			avgWaitTime = avgWaitTime/nProcess;
			avgTurnaroundTime = avgTurnaroundTime/nProcess;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(this.toString()+"\n");
	}

}
