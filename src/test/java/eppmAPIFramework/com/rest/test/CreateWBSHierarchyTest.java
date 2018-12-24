package eppmAPIFramework.com.rest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import eppmAPIFramework.com.rest.service.Service;

public class CreateWBSHierarchyTest {
	
Service service = new Service();
	
	/**
     * Assert to verify to Add the Qualify Instance 
     * 
     */
	@Test
	public void canCreateWBSHierarchy() {
		Assert.assertTrue(service.verifyCreateWBSHierarchy(),
				"Creation of WBS Elements Hierarchy Failed");
	}		

}
