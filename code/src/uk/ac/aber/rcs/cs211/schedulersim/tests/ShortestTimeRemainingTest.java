package uk.ac.aber.rcs.cs211.schedulersim.tests;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.rcs.cs211.schedulersim.Job;
import uk.ac.aber.rcs.cs211.schedulersim.scheduler.SchedulerException;
import uk.ac.aber.rcs.cs211.schedulersim.scheduler.ShortestTimeRemaining;

public class ShortestTimeRemainingTest {

	private ShortestTimeRemaining str;
	
	@Before
	public void setUp() {
		str = new ShortestTimeRemaining();
	}
	
	@Test
	public void testAddNewJob() {
		//create some jobs
		Job first = new Job("CPU1:2;4;10");
		Job second = new Job("CPU2:1;0;12");
		
		//add the longer job first
		try {
			str.addNewJob(second);
			str.addNewJob(first);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//Test if the shortest job is returned first
		try {
			Assert.assertTrue(str.getNextJob().equals(first));
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReturnJob() {
		//create some jobs
		Job first = new Job("CPU1:2;4;10");
		Job second = new Job("CPU2:1;0;12");
		
		//add the longer job first
		try {
			str.addNewJob(first);
			str.addNewJob(second);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//simulate some processing so it less than the other job
		for(int i=0;i<5;i++) {
			second.incrementProgramCounter();
		}
		
		//return the job to queue
		try {
			str.returnJob(second);
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
		
		//Check that the job is now the first
		try {
			Assert.assertTrue(str.getNextJob().equals(second));
		} catch (SchedulerException e) {
			fail(e.getMessage());
		}
	}

}
