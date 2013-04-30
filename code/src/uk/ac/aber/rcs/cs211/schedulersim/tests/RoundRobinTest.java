package uk.ac.aber.rcs.cs211.schedulersim.tests;

import static org.junit.Assert.fail;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import uk.ac.aber.rcs.cs211.schedulersim.Job;
import uk.ac.aber.rcs.cs211.schedulersim.scheduler.RoundRobin;
import uk.ac.aber.rcs.cs211.schedulersim.scheduler.SchedulerException;

public class RoundRobinTest {

	@Test
	public void testReturnJob() {
		Job first = new Job("CPU1:2;4;10");
		Job second = new Job("CPU2:1;0;12");
		RoundRobin rr = new RoundRobin();
		
		//add a couple of jobs
		try {
			rr.addNewJob(first);
			rr.addNewJob(second);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//return a job to the queue
		try {
			rr.returnJob(first);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//first job should now be moved to the back
		//therefore front is now equal to second
		try {
			Assert.assertTrue(rr.getNextJob().equals(second));
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testReturnJobThreeJobs() {
		Job first = new Job("CPU1:2;4;10");
		Job second = new Job("CPU2:1;0;12");
		Job third = new Job("MIXED1:2;0;5,2,2,2,6");
		RoundRobin rr = new RoundRobin();
		
		//add a couple of jobs
		try {
			rr.addNewJob(first);
			rr.addNewJob(second);
			rr.addNewJob(third);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//return a job to the queue
		try {
			rr.returnJob(first);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//first job should now be moved to the back
		//therefore order should be as in jobs array
		Job [] jobs = {second, third, first};
		Assert.assertTrue(Arrays.equals(rr.getJobList(), jobs));
	}
	
	@Test
	public void testReturnJobSingleJob() {
		Job first = new Job("CPU1:2;4;10");
		RoundRobin rr = new RoundRobin();
		
		//add a couple of jobs
		try {
			rr.addNewJob(first);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//return a job to the queue
		try {
			rr.returnJob(first);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//first job should now be moved to the back
		//therefore front is now equal to second
		try {
			Assert.assertTrue(rr.getNextJob().equals(first));
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
	}

}
