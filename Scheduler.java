import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Scheduler {
	public static final int CONTEXT_SWITCH = 5;
	
	protected File inputFile;
	String name;
	int lastProcTime;
	double cpuPerc;
	double throughput;
	double avgWaitTime;
	double avgTurnaroundTime;
	
	int nProcess;
	double cumulTime = 0;
	
	ArrayList<ProcessInfo> processed;
	
	public Scheduler(File file) {
		inputFile = file;
	}
	
	public abstract void run();
	
	public String toString() {
		String output = "\nScheduler: " + name;
		output += "\nTime when the last process finished: " + lastProcTime;
		output += "\nCPU Utilization: " + cpuPerc + "%";
		output += "\nThroughput: " + throughput + " process per second";
		output += "\nAverage Wait Time: " + avgWaitTime;
		output += "\nAverage Turnaround Time: " + avgTurnaroundTime;
		
		return output;
	}
	
	class burstComparator implements Comparator<ProcessInfo> {

		@Override
		public int compare(ProcessInfo p1, ProcessInfo p2) {
			if(p1.burstTime < p2.burstTime) {
				return -1;
			}
			else if(p1.burstTime > p2.burstTime) {
				return 1;
			}
			return 0;
		}
	}
	
	class idComparator implements Comparator<ProcessInfo> {

		@Override
		public int compare(ProcessInfo p1, ProcessInfo p2) {
			if(p1.pID < p2.pID) {
				return -1;
			}
			else if(p1.pID > p2.pID) {
				return 1;
			}
			return 0;
		}
	}
	
	class prioComparator implements Comparator<ProcessInfo> {

		@Override
		public int compare(ProcessInfo p1, ProcessInfo p2) {
			if(p1.priority < p2.priority) {
				return -1;
			}
			else if(p1.priority > p2.priority) {
				return 1;
			}
			return 0;
		}
	}
}
