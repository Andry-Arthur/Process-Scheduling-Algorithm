import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class PPriorityScheduler extends Scheduler {

	public PPriorityScheduler(File file) {
		super(file);
		name = "Priority";
	}

	@Override
	public void run() {
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
			nProcess = scanner.nextInt();

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

			PriorityQueue<ProcessInfo> ready = new PriorityQueue<>(new prioComparator());
			processed = new ArrayList<>();
			ProcessInfo onCpu = null;

			while(!processes.isEmpty() || !ready.isEmpty() || onCpu != null) {

				//get the ones arrived into ready queue
				ProcessInfo head = processes.peek();
				if(head != null && head.arrivalTime <= cumulTime) {
					head = processes.poll();
					ready.add(head);
				}

				if(!ready.isEmpty()) {
					//get the shortest job
					if(onCpu == null) {
						onCpu = ready.poll();
						onCpu.waitTime += CONTEXT_SWITCH;
						cumulTime += CONTEXT_SWITCH;
					}
					else {
						if(ready.peek().priority < onCpu.priority) {
							if(onCpu.burstTime > 0) {
								ready.add(onCpu);
							}
							onCpu = ready.poll();
							onCpu.waitTime+= CONTEXT_SWITCH;
							cumulTime += CONTEXT_SWITCH;
						}
					}
					for(ProcessInfo p:ready) {
						p.waitTime++;
					}
				}
				if(onCpu != null) {
					onCpu.burstTime--;
					if(onCpu.burstTime == 0) {
						onCpu.completionTime = (int) cumulTime;
						onCpu.turnAroundTime = onCpu.completionTime - onCpu.arrivalTime;
						processed.add(onCpu);
						onCpu = null;
					}
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
		
		System.out.print(this.toString()+"\n");
	}

}
