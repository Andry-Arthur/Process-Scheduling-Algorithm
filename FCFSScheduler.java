import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FCFSScheduler extends Scheduler {
	
	ProcessInfo[] processes;
	
	public FCFSScheduler(File file) {
		super(file);
		name = "FCSF";
	}

	@Override
	public void run() {
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
			nProcess = scanner.nextInt();
			processes = new ProcessInfo[nProcess];
			for(int i = 0; i < nProcess; ++i) {
				int id = scanner.nextInt();
				int priority = scanner.nextInt();
				int arrival = scanner.nextInt();
				int burst = scanner.nextInt();

				processes[i] = new ProcessInfo(id, priority, arrival, burst);
			}
			scanner.close();
			
			for(int i = 0; i < nProcess; ++i) {
				ProcessInfo curr = processes[i];
				curr.setWaitTime(cumulTime - curr.arrivalTime);
				cumulTime += curr.getBurstTime();
				curr.setCompletionTime((int) cumulTime);
				curr.setTurnAroundTime(curr.getCompletionTime() - curr.getArrivalTime());
				cumulTime += CONTEXT_SWITCH;
			}
			
			lastProcTime = (int) (cumulTime - CONTEXT_SWITCH);
			
			for(ProcessInfo p : processes) {
				cpuPerc += p.getBurstTime(); 
				avgWaitTime += p.getWaitTime();
				avgTurnaroundTime += p.getTurnAroundTime();
			}
		
			cpuPerc = cpuPerc / lastProcTime * 100;
			throughput = nProcess/ (lastProcTime/1000.0);
			avgWaitTime = avgWaitTime/nProcess;
			avgTurnaroundTime = avgTurnaroundTime/nProcess;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.toString()+"\n");
	}
}
