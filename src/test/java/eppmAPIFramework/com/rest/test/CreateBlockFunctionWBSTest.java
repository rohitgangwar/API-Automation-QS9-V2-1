package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test Cases for 
 * CREATE block functions for WBS
 * 
 */
public class CreateBlockFunctionWBSTest {
	
	Service service = new Service();

	/**
	 * Assert to Validate Creation of Block Functions
	 * for WBS Element for OVerhead Profile
	 * 
	 */
	@Test(priority = 1)
	public void canCreateBlockFunctionsForWBS() {
		Assert.assertTrue(service.verifyCreateWBSBlockFunctionOVH(),
				"Create Block Function API Failed for WBS Level for Overhead Project Profile");
	}

}
