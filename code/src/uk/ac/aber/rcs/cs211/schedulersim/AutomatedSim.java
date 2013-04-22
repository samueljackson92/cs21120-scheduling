package uk.ac.aber.rcs.cs211.schedulersim;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import uk.ac.aber.rcs.cs211.schedulersim.scheduler.LotteryScheduler;

public class AutomatedSim {
	Simulator sim;
	Scheduler scheduler;
	String filename;
	StringBuffer retval;
	
	public AutomatedSim() {
		int sampleSize = 1000;
		filename = "src/Test.jobs";
		retval = new StringBuffer();

		for(int i =0; i<sampleSize; i++) {
			ArrayList<Job> waitQueue = new ArrayList<Job>();
			
			try {
				waitQueue = SimulatorLoader.Load(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			scheduler = new LotteryScheduler();
			sim = new Simulator(scheduler, waitQueue);
			
			while (!sim.hasfinished()) {
				sim.singleStep();
			}
			makeResultsSummary(sim.getFinishedQueue());
			
			if((i+1)%100 == 0) {
				System.out.println("Finished test " + (i+1));
			}
		}
		
		writeResults(retval.toString());
	}
	
	private void writeResults(String output) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("output.csv")));
		    out.println(output);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		    System.exit(-1);
		}
	}
	
	private void makeResultsSummary(Job[] l) {
		Job temp;
		int total=0;
		for (int i=0; i<l.length; i++) {
			temp=l[i];
			total+=temp.getElapsedDuration();
		}
		
		//mean total duration
		retval.append(total/l.length);
		retval.append(",");
		
		//total CPU time
		retval.append(sim.getCpuTicks());
		retval.append(",");
		
		//total context switches
		retval.append(sim.getContextSwitchCount());
		retval.append(",");
		
		//total idle time
		retval.append(sim.getIdleTimeCount());
		retval.append("\n");
	}
	
	public static void main(String[] args) {
		new AutomatedSim();
	}
}
