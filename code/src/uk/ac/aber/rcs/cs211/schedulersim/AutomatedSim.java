package uk.ac.aber.rcs.cs211.schedulersim;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import uk.ac.aber.rcs.cs211.schedulersim.scheduler.LotteryScheduler;

/**
 * Automated Simulator.
 * Automate simulator for use with non-deterministic scheduling algorithms to
 * repeat the same test many times over to achieve consistent, reliable results.
 * @author slj11
 */
public class AutomatedSim {
	Simulator sim;
	Scheduler scheduler;
	String filename;
	StringBuffer retval;
	String outputName;
	
	/**
	 * Create an automated simulator.
	 * Requires the location of a .jobs file and the name of a file to
	 * output the results to.
	 * @param inputName the name of the .jobs file.
	 * @param outputName the name of the output file
	 */
	public AutomatedSim(String inputName, String outputName) {
		//This is the number of tests we wish to run
		int sampleSize = 10000;
		
		filename = inputName;
		this.outputName= outputName; 
		
		retval = new StringBuffer();
		
		//Add some meta information
		retval.append("# Automated Results file");
		retval.append("\n# Author: \t slj11");
		retval.append("\n# DataFile name:\t");
		retval.append(filename);
		retval.append("\n# " + new Date(System.currentTimeMillis()).toString());
		retval.append("\n# Number of Test Runs: " + sampleSize + "\n\n");
		
		//start running the tests
		for(int i =0; i<sampleSize; i++) {
			ArrayList<Job> waitQueue = new ArrayList<Job>();
			
			//load the jobs from file.
			try {
				waitQueue = SimulatorLoader.Load(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			//make fresh versions of the simulator and scheduler
			scheduler = new LotteryScheduler();
			sim = new Simulator(scheduler, waitQueue);
			
			//run this test until finished
			while (!sim.hasfinished()) {
				sim.singleStep();
			}
			
			//update output on every 100 tests
			if((i+1)%100 == 0) {
				System.out.println("Finished test " + (i+1));
			}
			
			makeResultsSummary(sim.getFinishedQueue());
		}
		
		//write the outcomes to file.
		writeResults(retval.toString());
	}
	
	/**
	 * Writes the results of the tests out to the given file name
	 * @param output
	 */
	private void writeResults(String output) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputName)));
		    out.println(output);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		    System.exit(-1);
		}
	}
	
	/**
	 * Makes a summary of the results.
	 * Creates it in CSV form and appends it to the output string buffer.
	 * @param l the list of finished jobs
	 */
	private void makeResultsSummary(Job[] l) {
		Job temp;
		int total=0;
		for (int i=0; i<l.length; i++) {
			temp=l[i];
			total+=temp.getElapsedDuration();
		}
		
		//first job duration
		retval.append(l[0].getElapsedDuration());
		retval.append(",");
		
		//first job finish time
		retval.append(l[0].getFinishTime());
		retval.append(",");
		
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
	
	/**
	 * Run the automated simulator.
	 * Requires two arguments: the .jobs file and the output file.
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
		if(args.length == 2) {
			new AutomatedSim(args[0], args[1]);
		} else {
			System.out.println("Program uses");
			System.out.println("Usage: AutomatedSim <input .jobs file> <output file name>");
		}
	}
}
