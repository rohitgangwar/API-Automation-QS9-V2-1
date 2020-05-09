package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test Cases for 
 * GET block functions for PSt & WBS
 * 
 */
public class GetBlockFunctionsTest {
	
	Service service = new Service();

	/**
	 * Assert to Validate GET Block Functions
	 * for PST
	 * 
	 */
	@Test(priority = 1)
	public void canGetBlockFunctionForPST() {
		Assert.assertTrue(service.verifyGetBFPST(), "GET Block Function API Failed for PST");
	}
	
	/**
	 * Assert to Validate GET Block Functions
	 * for Released PST
	 * 
	 */
	@Test(priority = 2)
	public void canGetBlockFunctionForReleasedPST() {
		Assert.assertTrue(service.verifyGetBFReleasedPST(), "GET Block Function API Failed for Released PST");
	}
	
	/**
	 * Assert to Validate GET Block Functions
	 * for Deleted PST
	 * 
	 */
	@Test(priority = 3)
	public void canNotGetBlockFunctionForDeletedPST() {
		Assert.assertTrue(service.verifyGetBFDeletedPST(), "GET Block Function API Failed for Released PST");
	}

}
