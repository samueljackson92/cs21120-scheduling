package uk.ac.aber.rcs.cs211.schedulersim.scheduler;

import uk.ac.aber.rcs.cs211.schedulersim.AbstractScheduler;
import uk.ac.aber.rcs.cs211.schedulersim.Job;

/**
 * A shortest job next scheduling algorithm.
 * Will the the first job from the queue and return it to the back of the queue. 
 * It will continue to cycle through all the jobs until they are all complete.
 * @author slj11
 * @see uk.ac.aber.rcs.cs211.schedulersim.Simulator
 */
public class ShortestJobNext extends AbstractScheduler {
	@Override
	public void addNewJob(Job job) throws SchedulerException {
		if (this.queue.contains(job)) throw new SchedulerException("Job already on Queue");
		
		for(int i=0; i<=this.numberOfJobs; i++) {
			if(i == this.numberOfJobs 
					|| job.getLength() < this.queue.get(i).getLength()) {
				
				this.queue.add(i, job);
				this.numberOfJobs++;
				break;
			}
		}
		

	}
}
