package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test Cases for 
 * CREATE block functions for PSt
 * 
 */
public class CreateBlockFunctionsPSTTest {
	
	Service service = new Service();

	/**
	 * Assert to Validate Creation of Single Block Functions
	 * for Overhead project
	 * 
	 */
	@Test(priority = 1)
	public void canCreateOVHBlockFunctionPST() {
		Assert.assertTrue(service.verifyCreateSingleBFPSTOVHProj(),
				"Create Block Function API Failed for Overhead Project");
	}
	
	/**
	 * Assert to Validate Creation of multiple Block Functions
	 * for Investment project
	 * 
	 */
	@Test(priority = 2)
	public void canCreateINVBlockFunctionPST() {
		Assert.assertTrue(service.verifyCreateMultipleBFPSTINVProj(),
				"Create Block Function API Failed for Investment Project");
	}
	
	/**
	 * Assert to Validate Creation of multiple Block Functions
	 * for Statistical project
	 * 
	 */
	@Test(priority = 3)
	public void canCreateStatProjBFPST() {
		Assert.assertTrue(service.verifyCreateMultipleBFPSTSTATProj(),
				"Create Block Function API Failed for Statistical Project");
	}
	
	/**
	 * Assert to Validate Creation of multiple Block Functions
	 * for Revenue project
	 * 
	 */
	@Test(priority = 4)
	public void canCreateRevProjBFPST() {
		Assert.assertTrue(service.verifyCreateMultipleBFPSTRevProj(),
				"Create Block Function API Failed for Revenue Project");
	}
	
	/**
	 * Assert to Validate Creation of multiple Block Functions
	 * for Overhead project in released Status
	 * 
	 */
	@Test(priority = 5)
	public void canCreateBFReleasedPST() {
		Assert.assertTrue(service.verifyCreateMultipleBFPSTReleasedProj(),
				"Create Block Function API Failed for Released PST");
	}

}
