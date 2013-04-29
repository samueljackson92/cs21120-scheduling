package uk.ac.aber.rcs.cs211.schedulersim.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.rcs.cs211.schedulersim.Job;
import uk.ac.aber.rcs.cs211.schedulersim.scheduler.LotteryScheduler;
import uk.ac.aber.rcs.cs211.schedulersim.scheduler.SchedulerException;

public class LotterySchedulerTest {
	LotteryScheduler ls;
	Job first;
	Job second;
	Job third;
	
	@Before
	public void setUp() {
		//invoke new scheduler instance with this
		//specific seed to guarantee same results
		ls = new LotteryScheduler(123);
		
		//make some jobs
		first = new Job("CPU1:2;4;10");
		second = new Job("CPU2:1;0;12");
		third = new Job("MIXED1:3;0;5,2,2,2,6");
	}
	
	@Test
	public void testAddNewJob() {			
		//add jobs to the queue
		try {
			ls.addNewJob(first);
			ls.addNewJob(second);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
	
		//check they've both been added and that second is now the first job.
		//seed should insure that it always returns the second job
		try {
			Assert.assertTrue(ls.getNextJob().equals(second));
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRemoveJob() {
		//add jobs to the queue
		try {
			ls.addNewJob(first);
			ls.addNewJob(second);
			ls.addNewJob(third);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//try removing a job in the queue
		try {
			ls.removeJob(second);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//check it was removed
		Assert.assertFalse(Arrays.asList(ls.getJobList()).contains(second));
	}

	@Test
	public void testReset() {
		//make some jobs
		Job first = new Job("CPU1:2;4;10");
		Job second = new Job("CPU2:1;0;12");
			
		//add them to the queue
		try {
			ls.addNewJob(first);
			ls.addNewJob(second);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//reset the queue
		ls.reset();
		
		//Queue should now be empty
		Assert.assertTrue(ls.getJobList().length == 0);
	}

	@Test
	public void testReturnJob() {
		//add them to the queue
		try {
			ls.addNewJob(first);
			ls.addNewJob(second);
			ls.addNewJob(third);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//try returning the job to the scheduler
		try {
			ls.returnJob(first);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//check that the job is now second
		//seed should insure this is always the case
		try {
			Assert.assertTrue(ls.getNextJob().equals(second));
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
	}

}
