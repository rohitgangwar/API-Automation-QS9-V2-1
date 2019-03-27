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

}
