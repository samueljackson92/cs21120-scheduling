package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.aber.rcs.cs211.schedulersim.AbstractScheduler;
import uk.ac.aber.rcs.cs211.schedulersim.Job;

/**
 * Lottery scheduling algorithm.
 * Runs a weighted lottery when a new item is added or a job is returned to the 
 * scheduler. Weighting is based on the priority of the job.
 * @author slj11
 */
public class LotteryScheduler extends AbstractScheduler {
	/**
	 * The weights for each job in the queue
	 */
	private ArrayList<Double> weights;
	
	/**
	 * The current highest priority of a job in the queue
	 */
	private int highestPriority = 0;
	private Random rnd;
	
	public LotteryScheduler(long seed) {
		this();
		this.rnd.setSeed(seed);
	}
	
	public LotteryScheduler() {
		super();
		weights = new ArrayList<Double>();
		rnd = new Random();
	}
	
	/**
	 * Adds a new job to the queue.
	 * This will insert the job at the back of a queue and run a lottery
	 * to pick the next job.
	 * @param job the job to be added
	 */
	@Override
	public void addNewJob(Job job) throws SchedulerException {
		if (this.queue.contains(job)) throw new SchedulerException("Job already on Queue");
		this.queue.add(numberOfJobs, job);
		this.weights.add(numberOfJobs, 0.0d);
		this.numberOfJobs++;
		
		calcWeights();
		lottery();
	}
	
	/**
	 * Returns a job to the queue.
	 * This will run a random weighted lottery to decide on the next job to be performed.
	 * @param job the job to be returned
	 */
	@Override
	public void returnJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		calcWeights();
		lottery();
	}
	
	/**
	 * Removes a job from the queue.
	 * This will remove the job from the queue, along with its weight and decrease the number
	 * of jobs by one.
	 * @param job the job to be removed
	 */
	@Override
	public void removeJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		this.weights.remove(queue.indexOf(job));
		this.queue.remove(job);
		this.numberOfJobs--;
	}
	
	/**
	 * Reset the scheduler.
	 * Clears all of the weights and resets the highest priority to zero.
	 */
	@Override
	public void reset() {
		super.reset();
		weights.clear();
		highestPriority = 0;
	}
	
	/**
	 * Run a lottery.
	 * Picks a random item from the queue based on the weighting 
	 * and moves it to the front.
	 */
	private void lottery() {
		int index = pickSlot();
		queue.add(0, queue.remove(index));
	}
	
	/**
	 * Normalise the weights.
	 * Normalises all the weights to be within the range 0-1
	 */
	private void normalise() {
		double lstSum = sum(weights);
		for(int i=0; i<numberOfJobs;i++) {
			weights.set(i, weights.get(i) / lstSum);
		}
	}
	
	/**
	 * Recalculates the weights of every item in the queue.
	 */
	private void calcWeights() {
		
		//calculate the new highest priority in the queue
		highestPriority = 0;
		for(Job j : queue) {
			if(j.getPriority() > highestPriority) {
				highestPriority = j.getPriority();
			}
		}
		
		//re calculate weight of each item
		for(int i =0; i<numberOfJobs; i++) {
			weights.set(i, calcWeight(queue.get(i)));
		}
		//correct weights to with range of 1-0
		normalise();
	}
	
	/**
	 * Calculates the weight for a give job.
	 * This will assume that lower numbered jobs have higher
	 * priority.
	 * @param job the job to calculate the weight for.
	 * @return the weighting of the job
	 */
	private double calcWeight(Job job) {
		double weight = 1.0d/numberOfJobs;
		weight *= (highestPriority - job.getPriority())+1;
		return weight;
	}
	
	/**
	 * Picks an job from the queue based its weight.
	 * @return the index of the job
	 */
	//Code taken from: http://stackoverflow.com/questions/6737283/weighted-randomness-in-java
	private int pickSlot() {
		int randomIndex = -1;
		double random = rnd.nextDouble();
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
	
	/**
	 * Sum all the double in an ArrayList
	 * @param lst the list to sum
	 * @return the sum of the list
	 */
	private double sum(ArrayList<Double> lst) {
		double sum = 0;
		
		for (double d:lst) {
			sum += d;
		}
		
		return sum;
	}
}
