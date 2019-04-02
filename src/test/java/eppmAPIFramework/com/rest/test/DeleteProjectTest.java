package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test cases for DELETION of Project for different profiles
 * 
 */
public class DeleteProjectTest {

	Service service = new Service();

	/**
	 * Assert to Validate Project Deletion Scenario for Investment Profile
	 * 
	 */
	@Test(priority=1)
	public void canDeleteInvestmentProject() {
		Assert.assertTrue(service.verifyDeleteInvestmentProject(), "Delete Project API Failed for Investment Project");
	}
	
	/**
	 * Assert to Validate Project Deletion Scenario for Overhead Profile
	 * 
	 */
	@Test(priority=2)
	public void canDeleteOverheadProject() {
		Assert.assertTrue(service.verifyDeleteOverheadProject(), "Delete Project API Failed for Overhead Project");
	}
	
	/**
	 * Assert to Validate Project Deletion Scenario for Statistical Profile
	 * 
	 */
	@Test(priority=3)
	public void canDeleteStatisticalProject() {
		Assert.assertTrue(service.verifyDeleteStatisticalProject(), "Delete Project API Failed for Statistical Project");
	}
	
	/**
	 * Assert to Validate Project Deletion Scenario for Revenue Profile
	 * 
	 */
	@Test(priority=4)
	public void canDeleteRevenueProject() {
		Assert.assertTrue(service.verifyDeleteRevenueProject(), "Delete Project API Failed for Revenue Project");
	}
	
	/**
	 * Assert to Validate Released Project Deletion Scenario
	 * 
	 */
	@Test(priority=5)
	public void canDeleteRELEASEDProject() {
		Assert.assertTrue(service.verifyDeleteRELProject(), "Delete Project API Failed for Released Project");
	}
	
	/**
	 * Assert to Validate TCHO Project Deletion Scenario
	 * 
	 */
	@Test(priority=6)
	public void canDeleteTCHOProject() {
		Assert.assertTrue(service.verifyDeleteTCHOProject(), "Delete Project API Failed for TCHO Project");
	}
	
	/**
	 * Assert to Validate LOCKED Project Deletion Scenario
	 * 
	 */
	@Test(priority=7)
	public void canDeleteLOCKEDProject() {
		Assert.assertTrue(service.verifyDeleteLockedProject(), "Delete Project API Failed for LOCKED Project");
	}
	
	/**
	 * Assert to Validate CLOSED Project Deletion Scenario
	 * 
	 */
	@Test(priority=8)
	public void canDeleteCLOSEDProject() {
		Assert.assertTrue(service.verifyDeleteClosedProject(), "Delete Project API Failed for CLOSED Project");
	}

	
}
