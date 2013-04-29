package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import uk.ac.aber.rcs.cs211.schedulersim.AbstractScheduler;
import uk.ac.aber.rcs.cs211.schedulersim.Job;

/**
 * A shortest time remaining scheduling algorithm.
 * Will the the first job from the queue and return it to the back of the queue. 
 * It will continue to cycle through all the jobs until they are all complete.
 * @author slj11
 * @see uk.ac.aber.rcs.cs211.schedulersim.Simulator
 */
public class ShortestTimeRemaining extends AbstractScheduler {

	/**
	 * Adds a new Job to the queue.
	 * This will add a new job onto the queue at the correct position
	 * @param job the job to add to the queue
	 */
	@Override
	public void addNewJob(Job job) throws SchedulerException {
		if (this.queue.contains(job)) throw new SchedulerException("Job already on Queue");
		findPlace(job);
	}
	
	/**
	 * Finds a jobs place in the queue relative to the amount of time it has remaining.
	 * This will place the job in a position relative to its currently remaining length
	 * @param job the job to insert into the queue
	 */
	private void findPlace(Job job) {
		int length = job.getLength() - (job.getProgramCounter()-1);
		for(int i=0; i<=this.numberOfJobs; i++) {
			if(i == this.numberOfJobs 
					|| length < this.queue.get(i).getLength()) {
				
				this.queue.add(i, job);
				this.numberOfJobs++;
				break;
			}
		}
	}
	

	/**
	 * Returns a job to the queue.
	 * This will move the job to the correct position in the queue relative to its 
	 * remaining length.
	 * @param job the job to return to the queue
	 */
	@Override
	public void returnJob(Job job) throws SchedulerException {
		removeJob(job);
		findPlace(job);
	}
}