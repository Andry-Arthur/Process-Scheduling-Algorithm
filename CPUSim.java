import java.io.File;

public class CPUSim {
	static boolean verbose = false;
	public static void main(String[] args) {
		
		System.out.println("args[0]: " + args[0]);
		System.out.println("args[1]: " + args[1]);

		if(args.length > 0) {
			String fileName = args[0];
			if(args[1] == "-v" || args.length == 2) {
				verbose = true;
			}
			
			File file = new File(fileName);	
			FCFSScheduler scheduler1 = new FCFSScheduler(file);
			Scheduler scheduler2 = new PSJFScheduler(file);
			Scheduler scheduler3 = new PPriorityScheduler(file);
			Scheduler scheduler4 = new RoundRobinScheduler(file);
			scheduler1.run();
			if(verbose) {
				for(ProcessInfo p:scheduler1.processes) {
					System.out.println(p.toString());
				}
			}
			scheduler2.run();
			if(verbose) {
				System.out.println();
				for(ProcessInfo p:scheduler2.processed) {
					System.out.println(p.toString());
				}
			}
			
			scheduler3.run();
			if(verbose) {
				System.out.println();
				for(ProcessInfo p:scheduler3.processed) {
					System.out.println(p.toString());
				}
			}
			
			scheduler4.run();
			if(verbose) {
				for(ProcessInfo p:scheduler4.processed) {
					System.out.println(p.toString());
				}
			}
		}
	}

}
