package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import java.util.ArrayList;

import uk.ac.aber.rcs.cs211.schedulersim.AbstractScheduler;
import uk.ac.aber.rcs.cs211.schedulersim.Job;

/**
 * Lottery scheduling algorithm.
 * Runs a weighted lottery when a new item is added or a job is returned to the 
 * scheduler. Weighting is based on the priority of the job.
 * @author slj11
 */
public class LotteryScheduler extends AbstractScheduler {
	ArrayList<Double> weights;
	int highestPriority = 0;
	
	public LotteryScheduler() {
		super();
		weights = new ArrayList<Double>();
	}
	
	@Override
	public void addNewJob(Job job) throws SchedulerException {
		if (this.queue.contains(job)) throw new SchedulerException("Job already on Queue");
		this.queue.add(numberOfJobs, job);
		this.weights.add(numberOfJobs, 0.0d);
		this.numberOfJobs++;
		
		calcWeights();
		lottery();
	}
	
	@Override
	public void returnJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		calcWeights();
		lottery();
	}
	
	@Override
	public void removeJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		this.weights.remove(queue.indexOf(job));
		this.queue.remove(job);
		this.numberOfJobs--;
	}
	
	@Override
	public void reset() {
		super.reset();
		weights.clear();
		highestPriority = 0;
	}
	
	private void lottery() {
		int index = pickSlot();
		queue.add(0, queue.remove(index));
	}
	
	private void normalize() {
		double lstSum = sum(weights);
		for(int i=0; i<numberOfJobs;i++) {
			weights.set(i, weights.get(i) / lstSum);
		}
	}
	
	private void calcWeights() {
		
		highestPriority = 0;
		for(Job j : queue) {
			if(j.getPriority() > highestPriority) {
				highestPriority = j.getPriority();
			}
		}
		
		for(int i =0; i<numberOfJobs; i++) {
			weights.set(i, calcWeight(queue.get(i)));
		}
		normalize();
	}
	
	private double calcWeight(Job job) {
		double weight = 1.0d/numberOfJobs;
		weight *= (highestPriority - job.getPriority())+1;
		return weight;
	}
	
	//Code taken from http://stackoverflow.com/questions/6737283/weighted-randomness-in-java
	private int pickSlot() {
		int randomIndex = -1;
		double random = Math.random();
		for (int i = 0; i < numberOfJobs; ++i)
		{
		    random -= weights.get(i);
		    if (random <= 0.0d)
		    {
		        randomIndex = i;
		        break;
		    }
		}
		
		return randomIndex;
	}
	
	private double sum(ArrayList<Double> lst) {
		double sum = 0;
		
		for (double d:lst) {
			sum += d;
		}
		
		return sum;
	}
}
