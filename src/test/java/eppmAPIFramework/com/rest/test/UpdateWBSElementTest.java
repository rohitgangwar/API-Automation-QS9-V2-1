package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

public class UpdateWBSElementTest {
	
Service service = new Service();
	
	/**
     * Assert to verify to Add the Qualify Instance 
     * 
     */
	@Test
	public void canUpdateWBSElementAttributeDescription() {
		Assert.assertTrue(service.verifyUpdateWBSAttributeDescription(),
				"Not able to update the WBS Element Attribute Description Value");
	}		
	

}
