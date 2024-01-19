
public class ProcessInfo {
	int pID;
	int priority;
	int arrivalTime;
	int burstTime;
	double waitTime;
	int turnAroundTime;
	int completionTime;
	
	int burstCounter; 
	public ProcessInfo(int pid, int priority, int arrival, int burst) {
		pID = pid; 
		this.priority = priority;
		arrivalTime = arrival;
		burstTime = burst;
		burstCounter = burst;
		waitTime = 0;
		completionTime = 0;
	}

	public int getpID() {
		return pID;
	}

	public void setpID(int pID) {
		this.pID = pID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public int getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public double getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(double d) {
		this.waitTime = d;
	}
	
	public String toString() {
		return pID + " " + arrivalTime + " "+ completionTime + " " + turnAroundTime + " " + waitTime; 
	}
}
