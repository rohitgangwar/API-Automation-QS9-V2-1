package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

/**
 * This class contains all Test cases for UPDATE Scenarios on Project/WBS 
 * 
 */
public class UpdateWBSElementTest {

	Service service = new Service();

	/**
	 * Assert to validate Update Description for WBS
	 * 
	 */
	@Test
	public void canUpdateWBSElementAttributeDescription() {
		Assert.assertTrue(service.verifyUpdateWBSAttributeDescription(),
				"Not able to update the WBS Element Attribute Description Value");
	}

}
