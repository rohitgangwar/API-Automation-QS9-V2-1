package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test cases for UPDATE Scenarios on 
 * Block functions for PST
 * 
 */
public class UpdateBlockFunctionPST {
	
	Service service = new Service();

	/**
	 * Assert to validate Update multiple values of Block funciton
	 * for PST
	 * 
	 */
	@Test(priority = 1)
	public void canUpdatePSTBlockFunctions() {
		Assert.assertTrue(service.verifyUpdateMultipleBlockFunctionPST(), "Not able to Update Multiple Block Functions for PST");
	}

}
