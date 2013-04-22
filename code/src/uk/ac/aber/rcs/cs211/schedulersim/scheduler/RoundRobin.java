package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import uk.ac.aber.rcs.cs211.schedulersim.*;

/**
 * A round robin scheduling algorithm.
 * Will the the first job from the queue and return it to the back of the queue. 
 * It will continue to cycle through all the jobs until they are all complete.
 * @author slj11
 * @see uk.ac.aber.rcs.cs211.schedulersim.Simulator
 */
public class RoundRobin extends AbstractScheduler {
	
	/**
	 * returns a job back to the queue.
	 * removes it from the front and re-inserts it at the back.
	 * @param job The job to return to the queue.
	 */
	@Override
	public void returnJob(Job job) throws SchedulerException {
		if (!this.queue.contains(job)) throw new SchedulerException("Job not on Queue");
		removeJob(job);
		addNewJob(job);
	}
}
